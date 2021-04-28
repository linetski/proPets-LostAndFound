package propets.lostAndFound.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;



@Component
public class AuthFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("entered");
		String token = (String) request.getHeader("Authorization");
		logger.info("token: " + token);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", token);
		HttpEntity<String> requestObject = 
			      new HttpEntity<String>(null, headers);
	    
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange("http://propets-auth-service/api/auth/authenticate", HttpMethod.GET, requestObject, String.class);
		if(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
			response.addHeader("401", "Unauthorized!");
			return;
		}
		logger.info("response from auth: " + responseEntity.toString());
		filterChain.doFilter(request, response);
	}



}
