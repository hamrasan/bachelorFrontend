package cz.fel.cvut.hamrasan.gardener.amqp.rpc;

import cz.fel.cvut.hamrasan.gardener.dao.GardenDao;
import cz.fel.cvut.hamrasan.gardener.dao.HumidityDao;
import cz.fel.cvut.hamrasan.gardener.dao.PressureDao;
import cz.fel.cvut.hamrasan.gardener.dao.TemperatureDao;
import cz.fel.cvut.hamrasan.gardener.model.Humidity;
import cz.fel.cvut.hamrasan.gardener.model.Pressure;
import cz.fel.cvut.hamrasan.gardener.model.Temperature;
import cz.fel.cvut.hamrasan.gardener.service.RpcService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StopWatch;

import java.time.LocalDate;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class Tut6Server {

    @Autowired
    private RpcService rpcService;


    @RabbitListener(queues = "tut.rpc.temperature")
//    @SendTo("tut.rpc.commands")
//    used when the client doesn't set replyTo.
    public void temperature(@Payload String n,
                              @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println(" [x] Received temperature for " + n);
        System.out.println(" [x] Received temperature is " + key);
//        String result = fib(n);
        rpcService.saveTemperatue(n,key);
    }

    @RabbitListener(queues = "tut.rpc.humidity")
    public void humidity(@Payload String n,
                         @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println(" [x] Received humidity is " + n);
        System.out.println(" [x] Received humidity is " + key);
        rpcService.saveHumidity(n,key);
    }

    @RabbitListener(queues = "tut.rpc.pressure")
    public void pressure(@Payload String n ,@Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println(" [x] Received pressure in hPa is " + n);
        System.out.println(" [x] Received pressure is in" + key);
        rpcService.savePress(n,key);
    }

    @RabbitListener(queues = "tut.rpc.rain")
    public void raining(@Payload String n ,@Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println(" [x] It is " + n + " raining in " + key);
        rpcService.saveRain(n,key);
    }

    @RabbitListener(queues = "tut.rpc.response")
//    @SendTo("tut.rpc.commands")
//    used when the client doesn't set replyTo.
    public void responses(String n) {
        System.out.println(" [x] Received request for " + n);
    }


    private void savePress(String n, String key){
//        float press = Float.parseFloat(n);
        String s = "";
        for (int i = 7; i < key.length(); i++) {
            s = s + n.charAt(i);
        }
//        long id = Long.parseLong(s);
    }

}