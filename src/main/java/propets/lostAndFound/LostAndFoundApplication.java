package propets.lostAndFound;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import propets.lostAndFound.mongodb.converters.ZonedDateTimeReadConverter;
import propets.lostAndFound.mongodb.converters.ZonedDateTimeWriteConverter;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan()
public class LostAndFoundApplication {
	
	/*
	 * @Value("${DOMAIN_NAME}") private String domainName;
	 * 
	 * @Value("${eureka.client.serviceUrl.defaultZone}") private String url;
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(LostAndFoundApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
	    List<Converter> list = new ArrayList<>();
	    list.add(new ZonedDateTimeReadConverter());
	    list.add(new ZonedDateTimeWriteConverter());
	    return new MongoCustomConversions(list);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		/*
		 * System.out.println(domainName); System.out.println(url);
		 */
	    
	}
}
