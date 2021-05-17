package propets.lostAndFound.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.LostPetRepository;
import propets.model.LostPet;

@Service
public class LostService {

	
	@Autowired
	LostPetRepository lostPetRepository;
	
	public void saveLostPet(LostPet lostPet) {
		lostPetRepository.save(lostPet);
	}

	public List<LostPet> getLostPets() {
		return lostPetRepository.findAll();	
	}
	
	public void removeAll() {
		lostPetRepository.deleteAll();
	}

	public Optional<LostPet> getLostPetById(String id) {
		return lostPetRepository.findById(id);
	}
}
