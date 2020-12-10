package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;

@Component
@RequiredArgsConstructor
public class MakeADeliveryFromAnOrder {

   private final OrderRepository orderRepository;
   private final DeliveryRepository deliveryRepository;
   private final UserRepository userRepository;

    public void makeADelivery(Order order, String address ){
        Delivery delivery = Delivery.builder().order(order).address(address).build();
        userRepository.save(order.getUser());
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }
}
