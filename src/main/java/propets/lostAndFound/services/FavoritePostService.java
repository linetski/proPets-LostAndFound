package propets.lostAndFound.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propets.lostAndFound.mongodb.lostAnimalRepository.FavoritePostRepository;
import propets.model.FavoritePost;
import propets.model.Post;


@Service
public class FavoritePostService {
	
	@Autowired
	FavoritePostRepository favoritePostRepository;
	
	public void saveFavoritePost(FavoritePost favoritePost) {
		favoritePostRepository.save(favoritePost);
	}

	public List<FavoritePost> getFavoritePosts() {
		return favoritePostRepository.findAll();	
	}
	
	public List<FavoritePost> getFavoritePostByEmail(String email) {
		return favoritePostRepository.findByEmail(email);
	}
	
	public void removeAll() {
		favoritePostRepository.deleteAll();
	}

	public Optional<FavoritePost> getPostById(String id) {
		return favoritePostRepository.findById(id);
	}
	
	public void deleteById(String id) {
		favoritePostRepository.deleteById(id);
		
	}
}
