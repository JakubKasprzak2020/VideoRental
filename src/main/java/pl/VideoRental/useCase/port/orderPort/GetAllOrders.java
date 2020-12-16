package pl.VideoRental.useCase.port.orderPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllOrders {

    private final OrderRepository orderRepository;

    public List<Order> getAll(){
        List <Order> orders = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }

}
