package cz.fel.cvut.hamrasan.gardener.amqp.rpc;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Gary Russell
 * @author Scott Deeg
 *
 */
@Profile({"tut6","rpc"})
@Configuration
public class Tut6Config {

//    @Profile("client")
//    private static class ClientConfig {
//
//        @Bean
//        public DirectExchange exchange() {
//            return new DirectExchange("tut.rpc");
//        }
//
//        @Bean
//        public Tut6Client client() {
//            return new Tut6Client();
//        }
//
//    }
@Bean
public FanoutExchange fanout() {
    return new FanoutExchange("tut.fanout");
}

    @Profile("client")
    private static class ClientConfig {

        @Bean(name="QueueCommmand")
        public Queue queueComm() {
//            return new AnonymousQueue();
            return new Queue("tut.rpc.commands");
        }

//        @Bean
//        @Qualifier("QueueCommmand")
//        public Binding binding2(DirectExchange directExchange,
//                                Queue queueComm) {
//            return BindingBuilder.bind(queueComm).to(fanout);
//        }

        @Bean
        public Tut6Client client() {
            return new Tut6Client();
        }
    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue queueInfo() {
//            return new AnonymousQueue();
            return new Queue("tut.rpc.info");
        }

        @Bean
        public Queue queueRes() {
//           return new AnonymousQueue();
            return new Queue("tut.rpc.response");
        }

//        @Bean
//        public DirectExchange exchangeServer() {
//            return new DirectExchange("tut.rpc");
//        }
//
//        @Bean
//        public Binding binding(DirectExchange exchange, Queue queue) {
//            return BindingBuilder.bind(queue).to(exchange).with("rpc");
//        }

        @Bean
        public Binding binding1(FanoutExchange fanout,
                                Queue queueInfo) {
            return BindingBuilder.bind(queueInfo).to(fanout);
        }

        @Bean
        public Tut6Server server() {
            return new Tut6Server();
        }

    }

}