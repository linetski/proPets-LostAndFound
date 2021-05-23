package propets.lostAndFound.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.PostRepository;
import propets.model.Post;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	public void savePost(Post post) {
		postRepository.save(post);
	}

	public List<Post> getPosts() {
		return postRepository.findAll();	
	}
	
	public List<Post> getPostByType(String type) {
		return postRepository.findByType(type);
	}
	
	public void removeAll() {
		postRepository.deleteAll();
	}

	public Optional<Post> getPostById(String id) {
		return postRepository.findById(id);
	}

}
