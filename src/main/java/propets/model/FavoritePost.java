package propets.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Document(collection = "favoritePosts")
public class FavoritePost {
	@Id
	String id;
	String email;
	Post post;
}
