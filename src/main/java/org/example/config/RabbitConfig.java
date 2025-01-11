package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue duplicadosQueue() {
        return new Queue("pedido-duplicado", true);
    }

    @Bean
    public Queue erroQueue() {
        return new Queue("pedido-erro", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("pedido-exchange");
    }

    @Bean
    public Binding duplicadosBinding(Queue duplicadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(duplicadosQueue).to(exchange).with("pedido.duplicado");
    }

    @Bean
    public Binding erroBinding(Queue erroQueue, TopicExchange exchange) {
        return BindingBuilder.bind(erroQueue).to(exchange).with("pedido.erro");
    }
}
