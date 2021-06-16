package propets.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@ToString
@Setter
@Getter
@Document(collection = "posts")
public class Post {
	
	@Id
	private String id;
	private List<String> imageUrls = new ArrayList<String>();
	private String type;
	private ZonedDateTime date;
	private String nameProfile;
	private String text;

}
