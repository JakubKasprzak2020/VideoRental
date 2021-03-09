package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.orderPort.GetOrderFromCatalog;
import pl.VideoRental.useCase.port.userPort.PromoteUserType;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateDeliveryFromAnOrder {

   private final OrderRepository orderRepository;
   private final DeliveryRepository deliveryRepository;
   private final GetOrderFromCatalog getOrderFromCatalog;
   private final UserRepository userRepository;
   private final PromoteUserType promoteUserType;

    public Delivery makeDelivery(Order order, String address ){
        Delivery delivery = Delivery.builder().order(order).address(address).build();

        orderRepository.save(order);
        deliveryRepository.save(delivery);
        addOrderCostToUsersAmountSpent(order.getUser(), order.getCost());
        promoteUserType.promote(order.getUser());
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


    private void addOrderCostToUsersAmountSpent(User user, BigDecimal cost){
        BigDecimal newAmountSpent = user.getAmountSpent().add(cost);
        user.setAmountSpent(newAmountSpent);
        userRepository.save(user);
    }


}
