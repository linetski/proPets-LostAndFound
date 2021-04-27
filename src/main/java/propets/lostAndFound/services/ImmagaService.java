package propets.lostAndFound.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class ImmagaService {
	
	String apiKey = "acc_b1632dc451bf8bb";
	String APISecret = "0ec00b38f91b5249273cca2fd15e2790";
	
	public String getTagsByUrlImage(String imageUrl) throws Exception{
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

		connectionInput.close();

		System.out.println(jsonResponse);
		return jsonResponse;
	}

}
