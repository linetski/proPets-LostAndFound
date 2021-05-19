package propets.lostAndFound.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.FoundPetRepository;
import propets.model.FoundPet;
import propets.model.LostPet;

@Service
public class FoundService {
	
	@Autowired
	FoundPetRepository foundPetRepository;
	
	public void saveFoundPet(FoundPet foundPet) {
		foundPetRepository.save(foundPet);
	}

	public Optional<FoundPet> getFoundPetById(String id) {
		return foundPetRepository.findById(id);
	}

	public void removeAll() {
		foundPetRepository.deleteAll();
	}
	
	public List<FoundPet> getFoundPets() {
		return foundPetRepository.findAll();	
	}
}
