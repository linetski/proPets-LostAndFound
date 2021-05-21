package propets.lostAndFound.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import propets.lostAndFound.controller.lostAndFoundController;
import propets.model.ImmagaResponse;
import propets.model.ImmagaTagWithConfidence;

@Service
public class ImmagaService {
	
	String apiKey = "acc_b1632dc451bf8bb";
	String APISecret = "0ec00b38f91b5249273cca2fd15e2790";
	
	private static final Logger logger = LoggerFactory.getLogger(ImmagaService.class);
	
	public HashSet<String> getTagsByUrlImage(String imageUrl) throws Exception{
		String credentialsToEncode = apiKey + ":" + APISecret;
		String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

		String endpoint_url = "https://api.imagga.com/v2/tags";
		String image_url = "https://upload.wikimedia.org/wikipedia/commons/0/0d/BIR_Grupp-_6_DALMATINER%2C_Mellanm%C3%B6llan_Honey_Pie_%2824234227655%29.jpg";

		//imageUrl = image_url;
		
		String url = endpoint_url + "?image_url=" + imageUrl;
		URL urlObject = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

		connection.setRequestProperty("Authorization", "Basic " + basicAuth);

		int responseCode = connection.getResponseCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String jsonResponse = connectionInput.readLine();
		StringBuilder responseTags = new StringBuilder();
		HashSet<String> set = new HashSet<String>();
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ImmagaResponse immagaResponse = om.readerFor(ImmagaResponse.class).readValue(jsonResponse);
		for (ImmagaTagWithConfidence immagaTagWithConfidence : immagaResponse.getResult().getTags()) {
			if (immagaTagWithConfidence.getConfidence()>20) {
				set.add(immagaTagWithConfidence.getTag().getEn());				
			}
		}
		
		connectionInput.close();
		
		logger.info(responseTags.toString());
		return set;
	}

}
