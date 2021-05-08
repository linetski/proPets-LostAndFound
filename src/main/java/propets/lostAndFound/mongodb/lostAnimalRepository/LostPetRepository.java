package propets.lostAndFound.mongodb.lostAnimalRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.model.LostPet;



public interface LostPetRepository extends MongoRepository<LostPet, String>{
	

}
