package be.pxl.services.queu;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewQueue {

    @Bean
    public Queue reviewQueue1() {
        return new Queue("reviewQueue1", false);
    }
}
