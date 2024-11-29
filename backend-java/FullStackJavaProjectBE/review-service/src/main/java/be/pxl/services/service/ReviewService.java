package be.pxl.services.service;

import be.pxl.services.controllers.requests.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void postReview(ReviewRequest review) {

        rabbitTemplate.convertAndSend("reviewQueue1", review);
    }

    @RabbitListener(queues = "postQueue1")
    public void listen(String in) {
        System.out.println("Message read from postQueue1 : " + in);
    }
}
