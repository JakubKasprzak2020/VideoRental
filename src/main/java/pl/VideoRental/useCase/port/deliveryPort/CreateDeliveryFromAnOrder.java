package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.orderPort.GetOrderFromCatalog;

@Component
@RequiredArgsConstructor
public class CreateDeliveryFromAnOrder {

   private final OrderRepository orderRepository;
   private final DeliveryRepository deliveryRepository;
   private final GetOrderFromCatalog getOrderFromCatalog;
   private final UserRepository userRepository;

    public Delivery makeDelivery(Order order, String address ){
        Delivery delivery = Delivery.builder().order(order).address(address).build();
       // userRepository.save(order.getUser()); - it looks that it's no necessary
        orderRepository.save(order);
        deliveryRepository.save(delivery);
        return delivery;
    }


    public Delivery makeDelivery(long orderId, String address) {
        try {
           Order order = getOrderFromCatalog.getById(orderId);
           return makeDelivery(order, address);
        } catch (OrderDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
