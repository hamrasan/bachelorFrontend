package cz.fel.cvut.hamrasan.gardener.amqp.rpc;


import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class Tut6Client {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("QueueCommmand")
    private Queue queue;

    int start = 0;


    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println(" [x] Requesting fib(" + start + ")");
        String message = "Kolko je stupnov!";
        this.template.convertAndSend(queue.getName(), message);
       // Integer response = (Integer) template.convertSendAndReceive(exchange.getName(), "rpc", start++);
        //System.out.println(" [.] Got '" + response + "'");
    }

}