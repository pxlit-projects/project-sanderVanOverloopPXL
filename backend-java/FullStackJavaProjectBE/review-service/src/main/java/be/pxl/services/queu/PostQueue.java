package be.pxl.services.queu;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostQueue {

    @Bean
    public Queue postQueue1() {
        return new Queue("postQueue1", false);
    }
}
