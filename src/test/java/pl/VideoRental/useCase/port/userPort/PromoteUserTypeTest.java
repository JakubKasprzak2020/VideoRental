package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.domain.UserType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class PromoteUserTypeTest {

    @Autowired
    PromoteUserType promoteUserType;
    @Autowired
    CreateUser createUser;
    @Autowired
    DeleteUser deleteUser;

    private UserSignInData newUserSignInData = UserSignInData.builder()
            .address("Elm street")
            .email("freddy@elmstreet.com")
            .lastName("Krueger")
            .name("Freddy")
            .password("sweet dreams")
            .build();

    @Test
    void shouldPromoteUserFromRegularToSilver(){
        //given
        User user = createUser.create(newUserSignInData);
        user.setAmountSpent(BigDecimal.valueOf(PromoteUserType.AMOUNT_FOR_SILVER_USER_TYPE));
        //when
        promoteUserType.promote(user);
        //then
        assertEquals(UserType.SILVER, user.getUserType());
        deleteUser.deleteById(user.getId());
    }

    @Test
    void shouldPromoteUserFromSilverToGold(){
        //given
        User user = createUser.create(newUserSignInData);
        user.setUserType(UserType.SILVER);
        user.setAmountSpent(BigDecimal.valueOf(PromoteUserType.AMOUNT_FOR_GOLD_USER_TYPE));
        //when
        promoteUserType.promote(user);
        //then
        assertEquals(UserType.GOLD, user.getUserType());
        deleteUser.deleteById(user.getId());
    }

    @Test
    void shouldPromoteUserFromGoldToPlatinum(){
        //given
        User user = createUser.create(newUserSignInData);
        user.setUserType(UserType.GOLD);
        user.setAmountSpent(BigDecimal.valueOf(PromoteUserType.AMOUNT_FOR_PLATINUM_USER_TYPE));
        //when
        promoteUserType.promote(user);
        //then
        assertEquals(UserType.PLATINUM, user.getUserType());
        deleteUser.deleteById(user.getId());
    }

    @Test
    void shouldPromoteUserFromRegularToPlatinum(){
        //given
        User user = createUser.create(newUserSignInData);
        user.setAmountSpent(BigDecimal.valueOf(PromoteUserType.AMOUNT_FOR_PLATINUM_USER_TYPE));
        //when
        promoteUserType.promote(user);
        //then
        assertEquals(UserType.PLATINUM, user.getUserType());
        deleteUser.deleteById(user.getId());
    }

    @Test
    void shouldNotPromoteUserWhenAmountIsInsufficient(){
        //given
        User user = createUser.create(newUserSignInData);
        user.setAmountSpent(BigDecimal.valueOf(100));
        //when
        promoteUserType.promote(user);
        //then
        assertEquals(UserType.REGULAR, user.getUserType());
        deleteUser.deleteById(user.getId());
    }


}