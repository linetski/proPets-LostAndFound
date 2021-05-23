package propets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;



@ToString
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}
