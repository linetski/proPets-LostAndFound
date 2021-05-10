package propets.model;

import com.fasterxml.jackson.annotation.JsonRootName;

public class ImmagaTagWithConfidence {
	
	double confidence;
	Tag tag;
	
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
