package propets.lostAndFound.mongodb.lostAnimalRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.model.FavoritePost;


public interface FavoritePostRepository extends MongoRepository<FavoritePost, String>{
	List<FavoritePost> findByEmail(String email);
}