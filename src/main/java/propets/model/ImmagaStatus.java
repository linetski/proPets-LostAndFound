package propets.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "status")
public class ImmagaStatus {
	
	String text;
	String type;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
