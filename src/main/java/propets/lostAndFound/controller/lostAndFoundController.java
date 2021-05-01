package propets.lostAndFound.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import propets.lostAndFound.filters.AuthFilter;
import propets.lostAndFound.mongodb.model.FoundPet;
import propets.lostAndFound.mongodb.model.LostPet;
import propets.lostAndFound.services.FoundService;
import propets.lostAndFound.services.ImmagaService;
import propets.lostAndFound.services.LostService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class lostAndFoundController {
	
	private static final Logger logger = LoggerFactory.getLogger(lostAndFoundController.class);
	
	private static final String TOPIC = "lostAnimal";
	
	@Autowired
	LostService lostService;
	
	@Autowired
	FoundService foundService;
	
	@Autowired
	ImmagaService immagaService;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
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
	public  ResponseEntity<String> saveFoundAnimal(@RequestBody FoundPet foundPet) {
		StringBuilder tagsBuilder = new StringBuilder();
		String tags = "";
		try {
			for (String url : foundPet.getImageUrls()) {
				tagsBuilder.append(immagaService.getTagsByUrlImage(url));
				tagsBuilder.append(" ");
			}
			tags = tagsBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occured in saveFoundAnimal", e);
		}
	
		foundPet.setTags(tags);
		foundService.saveFoundPet(foundPet);
		logger.info("found pet saved: " + foundPet);
		return ResponseEntity.ok("foundPet saved");
	}
	
	@PostMapping("/saveLostPet")
	public  ResponseEntity<String> saveLostPet(@RequestBody LostPet lostPet) {
		StringBuilder tagsBuilder = new StringBuilder();
		String tags = "";
		try {
			for (String url : lostPet.getImageUrls()) {
				tagsBuilder.append(immagaService.getTagsByUrlImage(url));
				tagsBuilder.append(" ");
			}
			tags = tagsBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occured in saveLostAnimal", e);
		}
	
		lostPet.setTags(tags);
		lostService.saveLostPet(lostPet);
		System.out.println(lostPet);
		return ResponseEntity.ok("lostPet saved");
	}
	
	@ResponseBody
	@GetMapping("/getFoundPets")
	public  FoundPet getFoundPets() {
		FoundPet foundPet = new FoundPet();
		return foundPet;
	}
	
	@ResponseBody
	@GetMapping("/getLostPets")
	public  List<LostPet> getLostPets() {
		return lostService.getLostPets();
	}
	
	@GetMapping("/produce")
	public String produce() {	
		System.out.println("produce called");
		/*
		 * try { kafkaTemplate.send(TOPIC, "dog lost"); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return "produce called";
	}
	
	/*
	 * @KafkaListener(topics = TOPIC, groupId = "foo") public void consume(String
	 * message) { System.out.println("Received Message in group foo: " + message); }
	 */
	
}
