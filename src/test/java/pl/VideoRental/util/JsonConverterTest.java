package pl.VideoRental.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonConverterTest {

    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GetAllUsers getAllUsers;

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

    @Test
    void shouldConvertToOrder() throws JsonProcessingException {
        //given
        Order order = Order.builder()
                .cost(BigDecimal.TEN)
                .id(9)
                .user(getAllUsers.getAll().get(0))
                .build();
        String json = objectMapper.writeValueAsString(order);
        //when
        Order orderFromJson = jsonConverter.getOrderFromJson(json);
        //then
        assertEquals(order.getId(), orderFromJson.getId());
        assertEquals(order.getCost(), orderFromJson.getCost() );
        assertEquals(order.getUser().getId(), orderFromJson.getUser().getId());
        assertNull(orderFromJson.getCopies());
    }

    @Test
    void shouldConvertToDelivery() throws JsonProcessingException {
        //given
        Delivery delivery = Delivery.builder()
                .address("MiddleEarth")
                .isDelivered(true)
                .build();
        String json = objectMapper.writeValueAsString(delivery);
        //when
        Delivery deliveryFromJson = jsonConverter.getDeliveryFromJson(json);
        //then
        assertEquals(delivery.getAddress(), deliveryFromJson.getAddress());
        assertEquals(delivery.isDelivered(), deliveryFromJson.isDelivered());
        assertNull(deliveryFromJson.getOrder());
    }


    @Test
    void shouldThrowExceptionWhenStringIsInappropriate(){
        //given
        String inappropriateString = "ihyuugyv";
        //then
        assertThrows(JsonSyntaxException.class, ()-> jsonConverter.getCopyFromJson(inappropriateString));
    }

}