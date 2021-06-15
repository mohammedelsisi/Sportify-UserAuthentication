package com.example.UserAuthentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@Slf4j
public class OutBoundConfig {

    private static final String QUEUE_NAME = "NewRegisteredUsers";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public MessageChannel newUsersMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "newUsersMessageChannel", outputChannel = "toRabbit")
    public ObjectToJsonTransformer objectToJsonTransformer() {
        return new ObjectToJsonTransformer();
    }

    @Bean
    public MessageChannel toRabbit() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "toRabbit")
    public AmqpOutboundEndpoint rabbitOutboundEndpoint(AmqpTemplate amqpTemplate) {
        var adapter = new AmqpOutboundEndpoint(amqpTemplate);
        adapter.setRoutingKey(QUEUE_NAME);
        return adapter;
    }

}
