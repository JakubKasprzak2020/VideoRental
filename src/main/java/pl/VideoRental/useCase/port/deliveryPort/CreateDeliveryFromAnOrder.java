package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;

@Component
@RequiredArgsConstructor
public class CreateDeliveryFromAnOrder {

   private final OrderRepository orderRepository;
   private final DeliveryRepository deliveryRepository;
   private final UserRepository userRepository;

    public Delivery makeADelivery(Order order, String address ){
        Delivery delivery = Delivery.builder().order(order).address(address).build();
       // userRepository.save(order.getUser());
        orderRepository.save(order);
        deliveryRepository.save(delivery);
        return delivery;
    }
}
