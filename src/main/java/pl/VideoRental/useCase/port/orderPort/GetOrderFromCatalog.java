package pl.VideoRental.useCase.port.orderPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetOrderFromCatalog {

    private final OrderRepository orderRepository;

    public Order getById(long id) throws OrderDoesNotExistException {
        return orderRepository.findById(id).orElseThrow(()-> new OrderDoesNotExistException(id));
    }
}
