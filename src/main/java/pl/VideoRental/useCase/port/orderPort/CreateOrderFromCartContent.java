package pl.VideoRental.useCase.port.orderPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CartIsEmptyException;
import pl.VideoRental.useCase.port.cartPort.EmptyACart;
import pl.VideoRental.useCase.port.copyPort.RentACopy;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateOrderFromCartContent {

    private final Cart cart;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;
    private final OrderRepository orderRepository;
    private final EmptyACart emptyACart;
    private final RentACopy rentACopy;


    @Transactional
    public Order makeAnOrder(User user) throws CartIsEmptyException {
        if (cart.getCopies().size() == 0){
            throw new CartIsEmptyException();
        }
        for (Copy copy : cart.getCopies()) {
            rentACopy.rent(copy, user);
        }
        userRepository.save(user);
        Order order = buildOrder(user);
        orderRepository.save(order);
      //  user.getOrders().add(order);
        emptyACart.empty(cart);
        return order;
    }


    private Order buildOrder(User user){
        return Order.builder()
                .user(user)
                .copies(cart.getCopies())
                .cost(cart.getToPay())
                .build();
    }

}
