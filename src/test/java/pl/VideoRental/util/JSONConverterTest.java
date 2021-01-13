package pl.VideoRental.util;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JSONConverterTest {

    @Autowired
    JSONConverter jsonConverter;

    @Test
    void shouldConvertToUser(){
        //given
        String userJson = "{\n" +
                "        \"id\": 876,\n" +
                "        \"name\": \"Anna\",\n" +
                "        \"lastName\": \"Kournikova\",\n" +
                "        \"password\": \"tenis\",\n" +
                "        \"email\": \"anna@kournikova.com\",\n" +
                "        \"address\": \"Miami Beach\",\n" +
                "        \"userType\": \"REGULAR\"\n" +
                "    }";
        //when
        User user = jsonConverter.getUserFromJson(userJson);
        //then
        assertEquals(876, user.getId());
        assertEquals("Anna", user.getName());
        assertEquals("Kournikova", user.getLastName());
        assertEquals("tenis", user.getPassword());
        assertEquals("anna@kournikova.com", user.getEmail());
        assertEquals("Miami Beach", user.getAddress());
        assertEquals(UserType.REGULAR, user.getUserType());

    }


    @Test
    void shouldConvertToCopy(){
        //given
        String copyJson = "{\n" +
                "        \"id\": 4,\n" +
                "        \"rentalDays\": 0,\n" +
                "        \"rentalDate\": null,\n" +
                "        \"movie\": {\n" +
                "            \"id\": 1,\n" +
                "            \"title\": \"Inglourious Basterds\",\n" +
                "            \"description\": \"Second World War in Europe...\",\n" +
                "            \"releaseDate\": \"2009-01-01\",\n" +
                "            \"genre\": \"ACTION\"\n" +
                "        },\n" +
                "        \"user\": null,\n" +
                "        \"available\": true\n" +
                "    }";
        //when
        Copy copy = jsonConverter.getCopyFromJson(copyJson);
        //then
        assertEquals(4, copy.getId());
        assertEquals(0, copy.getRentalDays());
        assertNull(copy.getRentalDate());
        assertEquals(1, copy.getMovie().getId());
        assertNull(copy.getUser());
        assertTrue(copy.isAvailable());
    }

    //TODO - test is not passing
    @Test
    void shouldConvertToOrder(){
        String orderJson = "{\n" +
                "        \"id\": 298,\n" +
                "        \"cost\": 29,\n" +
                "        \"user\": null,\n" +
                "        \"copies\": null,\n" +
                "        \"delivery\": null,\n" +
                "    }";
        //when
        Order order = jsonConverter.getOrderFromJson(orderJson);
        //then
        assertEquals(298, order.getId());
        assertEquals(BigDecimal.valueOf(29), order.getCost() );
        assertNull(order.getUser());
        assertNull(order.getDelivery());
        assertNull(order.getCopies());
    }



    @Test
    void shouldThrowExceptionWhenStringIsInappropriate(){
        //given
        String inappropriateString = "ihyuugyv";
        //then
        assertThrows(JsonSyntaxException.class, ()-> jsonConverter.getCopyFromJson(inappropriateString));
    }

}