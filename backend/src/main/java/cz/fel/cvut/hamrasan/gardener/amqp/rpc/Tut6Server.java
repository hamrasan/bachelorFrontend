package cz.fel.cvut.hamrasan.gardener.amqp.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class Tut6Server {

    @RabbitListener(queues = "tut.rpc.temperature")
    @SendTo("tut.rpc.commands")
//    used when the client doesn't set replyTo.
    public String temperature(String n) {
        System.out.println(" [x] Received temperature for " + n);
        String result = fib(n);
        System.out.println(" [.] Returned " + result);
        return result;
    }

    @RabbitListener(queues = "tut.rpc.humidity")
    public void humidity(String n) {
        System.out.println(" [x] Received humidity is " + n);
    }

    @RabbitListener(queues = "tut.rpc.pressure")
    public void pressure(String n) {
        System.out.println(" [x] Received pressure in hPa is " + n);

    }


    @RabbitListener(queues = "tut.rpc.response")
//    @SendTo("tut.rpc.commands")
//    used when the client doesn't set replyTo.
    public void responses(String n) {
        System.out.println(" [x] Received request for " + n);
    }

    public String fib(String n) {
        return n ;
    }

}