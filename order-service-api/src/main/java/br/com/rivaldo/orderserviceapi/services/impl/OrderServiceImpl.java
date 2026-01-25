package br.com.rivaldo.orderserviceapi.services.impl;

import br.com.rivaldo.models.dtos.OrderCreatedMessage;
import br.com.rivaldo.models.exceptions.ResourceNotFoundException;
import br.com.rivaldo.models.requests.CreateOrderRequest;
import br.com.rivaldo.models.requests.UpdateOrderRequest;
import br.com.rivaldo.models.responses.OrderResponse;
import br.com.rivaldo.models.responses.UserResponse;
import br.com.rivaldo.orderserviceapi.clients.UserServiceFeignClient;
import br.com.rivaldo.orderserviceapi.entities.Order;
import br.com.rivaldo.orderserviceapi.mapper.OrderMapper;
import br.com.rivaldo.orderserviceapi.repositories.OrderRepository;
import br.com.rivaldo.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.rivaldo.models.enums.OrderStatusEnum.CLOSED;
import static java.time.LocalDateTime.now;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Order findById(final Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found. Id: " + id + ", Type: " + Order.class.getSimpleName())
        );
    }

    @Override
    public void save(CreateOrderRequest request) {
        final var requester = validateUserId(request.requesterId());
        final var customer = validateUserId(request.customerId());
        final var entity = repository.save(mapper.fromRequest(request));

        log.info("Order created: {}", entity);

        rabbitTemplate.convertAndSend(
                "helpdesk",
                "rk.orders.create",
                new OrderCreatedMessage(mapper.fromEntity(entity), requester, customer)
        );
    }

    @Override
    public OrderResponse update(Long id, UpdateOrderRequest request) {
        validateUsers(request);

        Order entity = findById(id);
        entity = mapper.fromRequest(entity, request);

        if (entity.getStatus().equals(CLOSED)) {
            entity.setClosedAt(now());
        }

        return mapper.fromEntity(repository.save(entity));
    }

    private void validateUsers(UpdateOrderRequest request) {
        if (request.requesterId() != null) validateUserId(request.requesterId());
        if (request.customerId() != null) validateUserId(request.customerId());
    }

    @Override
    public void deleteBtyId(final Long id) {
        repository.delete(findById(id));
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Order> findAllPaged(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        );

        return repository.findAll(pageRequest);
    }

    UserResponse validateUserId(final String userId) {
        return userServiceFeignClient.findById(userId).getBody();
    }

}
