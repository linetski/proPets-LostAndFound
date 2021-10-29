package propets.lostAndFound;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Value("${rabbitmq.exchange.lostpet}")
	private String lostpetExchange;
	
	@Value("${rabbitmq.routingkey.lostpet}")
	private String lostpetRoutingkey;
	
	@Value("${rabbitmq.exchange.foundpet}")
	private String foundpetExchange;
	
	@Value("${rabbitmq.routingkey.foundpet}")
	private String foundpetRoutingkey;

	@Bean
	Binding binding() {
		BindingBuilder.bind(new Queue(lostpetRoutingkey, false)).to(new DirectExchange(lostpetExchange,false,false)).with(lostpetRoutingkey);
		return BindingBuilder.bind(new Queue(foundpetRoutingkey, false)).to(new DirectExchange(foundpetExchange,false,false)).with(foundpetRoutingkey);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

}
