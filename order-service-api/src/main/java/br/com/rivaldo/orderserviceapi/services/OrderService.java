package br.com.rivaldo.orderserviceapi.services;

import br.com.rivaldo.models.requests.CreateOrderRequest;
import br.com.rivaldo.models.requests.UpdateOrderRequest;
import br.com.rivaldo.models.responses.OrderResponse;
import br.com.rivaldo.orderserviceapi.entities.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(Long id, UpdateOrderRequest request);

    void deleteBtyId(final Long id);

    List<Order> findAll();

    Page<Order> findAllPaged(Integer page, Integer linesPerPage, String direction, String orderBy);
}
