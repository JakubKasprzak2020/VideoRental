package pl.VideoRental.useCase.port.orderPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;

@Component
@RequiredArgsConstructor
public class UpdateOrder {

    /**
     * The only thing that could be changed by this method is cost of order.
     */

    private final GetOrderFromCatalog getOrderFromCatalog;
    private final OrderRepository orderRepository;

    public void update(long id, Order newOrder) {
        try {
            Order oldOrder = getOrderFromCatalog.getById(id);
            oldOrder.setCost(newOrder.getCost());
            orderRepository.save(oldOrder);
        } catch (OrderDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }
}
