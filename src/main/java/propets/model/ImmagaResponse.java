package propets.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

public class ImmagaResponse {
	
	ImmagaResult result;
	ImmagaStatus status;
	
	public ImmagaResult getResult() {
		return result;
	}
	public void setResult(ImmagaResult result) {
		this.result = result;
	}
	public ImmagaStatus getStatus() {
		return status;
	}
	public void setStatus(ImmagaStatus status) {
		this.status = status;
	}
	


}
