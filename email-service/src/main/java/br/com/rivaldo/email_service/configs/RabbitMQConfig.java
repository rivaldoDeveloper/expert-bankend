package br.com.rivaldo.email_service.configs;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        SimpleMessageConverter messageConverter = new SimpleMessageConverter();
        messageConverter.setAllowedListPatterns(List.of(
                "br.com.rivaldo.models.*",
                "java.util.*",
                "java.time.*",
                "java.lang.*"
        ));
        return messageConverter;
    }

}
