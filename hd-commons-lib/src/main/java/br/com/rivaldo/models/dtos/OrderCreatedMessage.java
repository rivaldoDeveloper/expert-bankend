package br.com.rivaldo.models.dtos;

import br.com.rivaldo.models.responses.OrderResponse;
import br.com.rivaldo.models.responses.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private OrderResponse order;
    private UserResponse customer;
    private UserResponse requester;
}
