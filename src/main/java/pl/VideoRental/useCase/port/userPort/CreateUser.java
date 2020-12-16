package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CartRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.domain.UserType;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void create(UserSignInData userSignInData){
        User user = User.builder()
                .name(userSignInData.getName())
                .lastName(userSignInData.getLastName())
                .password(userSignInData.getPassword())
                .email(userSignInData.getEmail())
                .address(userSignInData.getAddress())
                .cart(new Cart())
                .userType(UserType.REGULAR)
                .build();

        cartRepository.save(user.getCart());
        userRepository.save(user);
    }



}
