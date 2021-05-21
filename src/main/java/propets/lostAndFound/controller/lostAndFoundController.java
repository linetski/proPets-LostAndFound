package propets.lostAndFound.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import propets.lostAndFound.services.FoundService;
import propets.lostAndFound.services.ImmagaService;
import propets.lostAndFound.services.LostService;
import propets.model.FoundPet;
import propets.model.LostPet;


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
	ImmagaService immagaService;
	
	@Autowired
    private KafkaTemplate<String, LostPet> lostPetkafkaTemplate;
	
	@Autowired
    private KafkaTemplate<String, FoundPet> foundPetkafkaTemplate;

	
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
	@GetMapping("/getLostPets")
	public  List<LostPet> getLostPets() {
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
