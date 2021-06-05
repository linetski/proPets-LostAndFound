package propets.lostAndFound.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.client.http.HttpRequest;

import propets.lostAndFound.filters.AuthFilter;
import propets.lostAndFound.services.FavoritePostService;
import propets.lostAndFound.services.FoundService;
import propets.lostAndFound.services.ImmagaService;
import propets.lostAndFound.services.LostService;
import propets.lostAndFound.services.PostService;
import propets.model.FavoritePost;
import propets.model.FoundPet;
import propets.model.LostPet;
import propets.model.Post;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class lostAndFoundController {
	
	private static final Logger logger = LoggerFactory.getLogger(lostAndFoundController.class);
	
	private static final String LOST_PET_TOPIC = "lostpet";
	private static final String FOUND_PET_TOPIC = "foundpet";
	
	@Autowired
	LostService lostService;
	
	@Autowired
	FoundService foundService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	FavoritePostService favoritePostService;
	
	@Autowired
	ImmagaService immagaService;
	
	@Autowired
    private KafkaTemplate<String, LostPet> lostPetkafkaTemplate;
	
	@Autowired
    private KafkaTemplate<String, FoundPet> foundPetkafkaTemplate;

	private PostService postService2;

	private PostService postService3;

	
	@PostMapping("/upload")
	  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(message);
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    }
	  }
		
	@PostMapping("/saveFoundPet")
	public  ResponseEntity<String> saveFoundAnimal(@RequestBody FoundPet foundPet,HttpServletRequest req) {
		StringBuilder tagsBuilder = new StringBuilder();
		String tags = "";
		HashSet<String> set = new HashSet<String>();
		try {
			for(String url : foundPet.getImageUrls()) {
				set.addAll(immagaService.getTagsByUrlImage(url));
			}
			
			for (String tag : set) {
				tagsBuilder.append(tag);
				tagsBuilder.append(" ");
			}
			tags = tagsBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occured in saveFoundAnimal", e);
		}
	
		foundPet.setTags(tags);
		foundPet.setEmail((String)req.getSession().getAttribute("email"));
		
		foundService.saveFoundPet(foundPet);
		foundPetkafkaTemplate.send(FOUND_PET_TOPIC,foundPet);
		logger.info("found pet saved: " + foundPet);
		return ResponseEntity.ok("foundPet saved");
	}
	
	@PostMapping("/saveLostPet")
	public  ResponseEntity<String> saveLostPet(@RequestBody LostPet lostPet,HttpServletRequest req) {
		StringBuilder tagsBuilder = new StringBuilder();
		String tags = "";
		HashSet<String> set = new HashSet<String>();
		
		
		try {
			for(String url : lostPet.getImageUrls()) {
				set.addAll(immagaService.getTagsByUrlImage(url));
			}
			
			for (String tag : set) {
				tagsBuilder.append(tag);
				tagsBuilder.append(" ");
			}
			tags = tagsBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occured in saveLostAnimal", e);
		}
	
		lostPet.setTags(tags);
		String email =(String) req.getSession().getAttribute("email");
		logger.info(email);
		lostPet.setEmail(email);
		lostService.saveLostPet(lostPet);
		lostPetkafkaTemplate.send(LOST_PET_TOPIC,lostPet);
		logger.info(lostPet.toString());
		return ResponseEntity.ok("lostPet saved");
	}
	
	@ResponseBody
	@GetMapping("/getFoundPets")
	public  List<FoundPet> getFoundPets() {
		return foundService.getFoundPets();
	}
	
	@ResponseBody
	@GetMapping("/importFoundPetsToElastic")
	public  List<FoundPet> importFoundPetsToElastic() {
		List<FoundPet> list = foundService.getFoundPets();
		for(FoundPet pet: list) {
			foundPetkafkaTemplate.send(FOUND_PET_TOPIC,pet);
		}
		return foundService.getFoundPets();
	}
	
	@ResponseBody
	@GetMapping("/getLostPets")
	public  List<LostPet> getLostPets() {
		return lostService.getLostPets();
	}
	
	@ResponseBody
	@GetMapping("/importLostPetsToElastic")
	public  List<LostPet> importLostPetsToElastic() {
		List<LostPet> list = lostService.getLostPets();
		for(LostPet pet: list) {
			lostPetkafkaTemplate.send(LOST_PET_TOPIC,pet);
		}
		return lostService.getLostPets();
	}
	
	@ResponseBody
	@GetMapping("/getLostPetById/{id}")
	public  Optional<LostPet> getLostPetById(@PathVariable("id") String id) {
		return lostService.getLostPetById(id);
	}
	
	@ResponseBody
	@GetMapping("/getFoundPetById/{id}")
	public  Optional<FoundPet> getFoundPetById(@PathVariable("id") String id) {
		return foundService.getFoundPetById(id);
	}
	
	@ResponseBody
	@GetMapping("/getPosts/{type}")
	public  List<Post> getPosts(@PathVariable("type") String type) {
		return postService.getPostByType(type);
	}
	
	@ResponseBody
	@GetMapping("/getFavorites")
	public  List<FavoritePost> getFavorites(HttpServletRequest req) {
		String email =(String) req.getSession().getAttribute("email");
		List<FavoritePost> list = favoritePostService.getFavoritePostByEmail(email);
		/*
		 * List<Post> favoritePosts = list.stream() .map(favoritePost ->
		 * favoritePost.getPost()) .collect(Collectors.toList());
		 */
		
		return list;
	}
	
	@DeleteMapping("/favorites/{id}")
	void deleteFavoriteById(@PathVariable String id) {
		favoritePostService.deleteById(id);
	}
	
	@PostMapping("/saveFavoritePost")
	public  ResponseEntity<FavoritePost> saveFavoritePost(@RequestBody FavoritePost favoritePost, HttpServletRequest req) {
		String profileName =(String) req.getSession().getAttribute("profileName");
		String email =(String) req.getSession().getAttribute("email");
		favoritePost.setEmail(email);
		favoritePost = favoritePostService.saveFavoritePost(favoritePost);
		return ResponseEntity.ok(favoritePost);
	}
	
	@PostMapping("/savePost")
	public  ResponseEntity<String> savePost(@RequestBody Post post, HttpServletRequest req) {
		post.setDate(new Date());
		String profileName =(String) req.getSession().getAttribute("profileName");
		post.setNameProfile(profileName);
		postService.savePost(post);
		return ResponseEntity.ok("post saved");
	}
	
	@GetMapping("/produce")
	public String produce(@RequestBody LostPet lostPet) {	
		System.out.println("produce called");
		try { 
			lostPetkafkaTemplate.send(LOST_PET_TOPIC, lostPet); 
		} catch (Exception e) { // TODO
			e.printStackTrace(); 
		}
		return "produce called";
	}
	
	@GetMapping("/clearLostPets")
	public String clearLostPets() {	
		lostService.removeAll();
		return "removedAll";
	}
	
	@GetMapping("/clearFoundPets")
	public String clearFoundPets() {	
		foundService.removeAll();
		return "removedAll";
	}
	
	/*
	 * @KafkaListener(topics = TOPIC, groupId = "foo") public void consume(String
	 * message) { System.out.println("Received Message in group foo: " + message); }
	 */
	
}
