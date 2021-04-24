package propets.lostAndFound.mongodb.lostAnimalRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.lostAndFound.mongodb.model.LostPet;



public interface LostPetRepository extends MongoRepository<LostPet, String>{
	

}
