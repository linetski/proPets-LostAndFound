package propets.model;

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
	
	public static final String HOTEL_TYPE = "hotel";
	public static final String HOME_TYPE = "home";
	public static final String CARE_TYPE = "care";
	
	@Id
	private String id;
	private List<String> imageUrls = new ArrayList<String>();
	private String type;
	private Date date;
	private String profileName;

}
