package propets.lostAndFound.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.LostPetRepository;
import propets.lostAndFound.mongodb.model.LostPet;

@Service
public class LostService {
	
	@Autowired
	AWSS3Service awss3Service;
	
	@Autowired
	LostPetRepository lostPetRepository;
	
	@Value("${S3URL}")
	String URL;
	
	@Value("${LOST_FOLDER}")
	String LOST_FOLDER;
	
	public void saveLostPet(LostPet lostPet) {
		lostPetRepository.save(lostPet);
	}
}
