package propets.lostAndFound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan()
public class LostAndFoundApplication {

	public static void main(String[] args) {
		SpringApplication.run(LostAndFoundApplication.class, args);
	}

}
