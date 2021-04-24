package propets.lostAndFound.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import propets.lostAndFound.mongodb.model.FoundPet;
import propets.lostAndFound.mongodb.model.LostPet;
import propets.lostAndFound.services.FoundService;
import propets.lostAndFound.services.ImmagaService;
import propets.lostAndFound.services.LostService;



@RestController
public class lostAndFoundController {
	
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
		String tags = null;
		try {
			tags = immagaService.getTagsByUrlImage("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		foundPet.setTags(tags);
		foundService.saveFoundPet(foundPet);
		System.out.println(foundPet);
		return ResponseEntity.ok("foundPet saved");
	}
	
	@PostMapping("/saveLostPet")
	public  ResponseEntity<String> saveLostPet(@RequestBody LostPet lostPet) {
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
	public  LostPet getLostPets() {
		LostPet lostPet = new LostPet();
		return lostPet;
	}
	
	@GetMapping("/produce")
	public String produce() {	
		System.out.println("produce called");
		try {
			kafkaTemplate.send(TOPIC, "dog lost");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "hello";
	}
	
	@KafkaListener(topics = TOPIC, groupId = "foo")
	public void consume(String message) {
	    System.out.println("Received Message in group foo: " + message);
	}
	
}
