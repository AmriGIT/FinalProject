package co.amri.tokoshop.tokoshop;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class TokoshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokoshopApplication.class, args);
	}
        
        public static final String TOPIC_EXCHANGE_NAME = "tokoshop-exchange";
        
        public static final String QUEUE_NAME = "tokoshop-cart-queue";
        
        @Bean
        public Queue initializeQueue(){
        return new Queue(QUEUE_NAME, false);
        }
        
        @Bean
        public TopicExchange initializeTopicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
        }
        
        @Bean
        public Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("tokoshop.#");
        }
        
        @Bean
        public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
        }
        
        public AmqpTemplate rabbAmqpTemplate(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate =  new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
        }

}
