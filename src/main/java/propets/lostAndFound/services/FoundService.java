package propets.lostAndFound.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.FoundPetRepository;
import propets.lostAndFound.mongodb.model.FoundPet;

@Service
public class FoundService {
	
	@Autowired
	FoundPetRepository foundPetRepository;
	
	public void saveFoundPet(FoundPet foundPet) {
		foundPetRepository.save(foundPet);
	}
}
