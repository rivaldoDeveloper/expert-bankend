package br.com.rivaldo.email_service.listeners;

import br.com.rivaldo.email_service.models.enums.OperationEnum;
import br.com.rivaldo.email_service.service.EmailService;
import br.com.rivaldo.models.dtos.OrderCreatedMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.rivaldo.email_service.models.enums.OperationEnum.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final EmailService emailService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "helpdesk", type = "topic"),
            value = @Queue(value = "queue.orders"),
            key = "rk.orders.create"
    ))
    public void listener(final OrderCreatedMessage message) throws MessagingException {
        log.info("Order de serviço processada com sucesso: {}", message);
        emailService.sendHtmlMail(message, ORDER_CREATED);
    }
}
