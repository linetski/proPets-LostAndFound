package propets.lostAndFound.mongodb.lostAnimalRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import propets.lostAndFound.mongodb.model.FoundPet;


public interface FoundPetRepository extends MongoRepository<FoundPet, String>{

}
