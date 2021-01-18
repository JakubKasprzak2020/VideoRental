package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.port.orderPort.*;
import pl.VideoRental.util.JsonConverter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetOrderFromCatalog getOrderFromCatalog;
    @MockBean
    private GetAllOrders getAllOrders;
    @MockBean
    private CreateOrderFromCartContent createOrderFromCartContent;
    @MockBean
    private DeleteOrder deleteOrder;
    @MockBean
    private UpdateOrder updateOrder;
    @MockBean
    private JsonConverter jsonConverter;


    @Test
    void shouldGetAllOrders() throws Exception {
        //given
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);
        String url = "/api/orders";
        //when
        Mockito.when(getAllOrders.getAll()).thenReturn(orders);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(orders);
        assertEquals(expectedJsonResponse, actualJsonResponse);
        Mockito.verify(getAllOrders, Mockito.times(1)).getAll();
    }

    @Test
    void shouldGetAnOrder() throws Exception {
        //given
        long randomId = 8;
        Order order = new Order();
        order.setCost(BigDecimal.TEN);
        String url = "/api/orders/" + randomId;
        //when
        Mockito.when(getOrderFromCatalog.getById(Mockito.any(Long.class))).thenReturn(order);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertEquals(expectedJsonResponse, actualJsonResponse);
        Mockito.verify(getOrderFromCatalog, Mockito.times(1))
                .getById(Mockito.any(Long.class));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        //given
        long randomId = 16;
        String url = "/api/orders/" + randomId;
        //when
        Mockito.doNothing().when(deleteOrder).deleteById(Mockito.any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteOrder, Mockito.times(1))
                .deleteById(Mockito.any(Long.class));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        //given
        Order order = new Order();
        order.setCost(BigDecimal.TEN);
        order.setId(98);
        long randomId = 1;
        String url = "/api/orders/" + randomId;
        //when
        Mockito.doNothing().when(updateOrder).update(any(Long.class), any(Order.class));
        Mockito.when(jsonConverter.getOrderFromJson(any(String.class))).thenReturn(order);
        RequestBuilder request = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(order));
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(updateOrder, times(1))
                .update(any(Long.class), any(Order.class));
    }

    //TODO - waiting for Spring Security context
    @Test
    void shouldCreateOrder() {

    }


}