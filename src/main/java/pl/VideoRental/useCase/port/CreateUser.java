package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CartRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserType;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void create(String name, String lastName, String password, String email, String address){
        User user = User.builder()
                .name(name)
                .lastName(lastName)
                .password(password)
                .email(email)
                .address(address)
                .cart(new Cart())
                .userType(UserType.REGULAR)
                .build();

        cartRepository.save(user.getCart());
        userRepository.save(user);
    }



}
