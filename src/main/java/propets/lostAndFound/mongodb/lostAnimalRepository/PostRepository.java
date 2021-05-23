package propets.lostAndFound.mongodb.lostAnimalRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.model.Post;

public interface PostRepository extends MongoRepository<Post, String>{
	List<Post> findByType(String type);
}
