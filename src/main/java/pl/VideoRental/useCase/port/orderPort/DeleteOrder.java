package pl.VideoRental.useCase.port.orderPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteOrder {

    private final OrderRepository orderRepository;
    private final GetOrderFromCatalog getOrderFromCatalog;


    public void deleteById(long id){
        try {
            getOrderFromCatalog.getById(id);
            orderRepository.deleteById(id);
        } catch (OrderDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }


}
