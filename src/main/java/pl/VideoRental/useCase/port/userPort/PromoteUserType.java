package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserType;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PromoteUserType {

    /**
     * promote method is invoked in CreateDeliveryFromAnOrder class in makeDelivery method
     */

    private final UserRepository userRepository;

    public static final int AMOUNT_FOR_SILVER_USER_TYPE = 300;
    public static final int AMOUNT_FOR_GOLD_USER_TYPE = 1000;
    public static final int AMOUNT_FOR_PLATINUM_USER_TYPE = 2000;

    public void promote(User user){
        int amountSpent = user.getAmountSpent().intValue();
        UserType userType = user.getUserType();
        boolean wasChangeMade = false;

        if (!userType.equals(UserType.PLATINUM) && amountSpent >= AMOUNT_FOR_PLATINUM_USER_TYPE) {
            user.setUserType(UserType.PLATINUM);
            wasChangeMade = true;
        } else if ((userType.equals(UserType.REGULAR) || userType.equals(UserType.SILVER)) && amountSpent >= AMOUNT_FOR_GOLD_USER_TYPE){
            user.setUserType(UserType.GOLD);
            wasChangeMade = true;
        } else if (userType.equals(UserType.REGULAR) && amountSpent >= AMOUNT_FOR_SILVER_USER_TYPE){
            user.setUserType(UserType.SILVER);
            wasChangeMade = true;
        }

        if (wasChangeMade) {
            userRepository.save(user);
        }

    }


}
