package br.com.rivaldo.orderserviceapi.controllers.impl;

import br.com.rivaldo.models.requests.CreateOrderRequest;
import br.com.rivaldo.models.requests.UpdateOrderRequest;
import br.com.rivaldo.models.responses.OrderResponse;
import br.com.rivaldo.orderserviceapi.controllers.OrderController;
import br.com.rivaldo.orderserviceapi.mapper.OrderMapper;
import br.com.rivaldo.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok().body(mapper.fromEntity(service.findById(id)));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(mapper.fromEntities(service.findAll()));
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        service.deleteBtyId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAllPaged(Integer page, Integer linesPerPage, String direction, String orderBy) {
        return ResponseEntity.ok().body(service.findAllPaged(page, linesPerPage, direction, orderBy).map(mapper::fromEntity));
    }
}
