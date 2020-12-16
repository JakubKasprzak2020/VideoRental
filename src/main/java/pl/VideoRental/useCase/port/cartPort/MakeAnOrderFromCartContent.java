package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.cartPort.EmptyACart;
import pl.VideoRental.useCase.port.copyPort.RentACopy;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MakeAnOrderFromCartContent {

    private final UserRepository userRepository;
    private final CopyRepository copyRepository;
    private final OrderRepository orderRepository;
    private final EmptyACart emptyACart;
    private final RentACopy rentACopy;


    @Transactional
    public void makeAnOrder(User user){
        List<Copy> copies = user.getCart().getCopies();
        for (Copy copy : copies) {
            rentACopy.rent(copy, user);
        }
        Order order = buildOrder(user);
        orderRepository.save(order);
        user.getOrders().add(order);
        emptyACart.empty(user.getCart());
        userRepository.save(user);
    }


    private Order buildOrder(User user){
        return Order.builder()
                .user(user)
                .copies(user.getCart().getCopies())
                .cost(user.getCart().getToPay())
                .build();
    }

}
