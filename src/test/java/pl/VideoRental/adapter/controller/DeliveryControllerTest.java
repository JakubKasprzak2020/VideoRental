package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.authentication.UserDetailsServiceImpl;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.mail.EmailContent;
import pl.VideoRental.mail.EmailContentCreator;
import pl.VideoRental.mail.EmailService;
import pl.VideoRental.useCase.port.deliveryPort.*;
import pl.VideoRental.useCase.port.orderPort.GetOrderFromCatalog;
import pl.VideoRental.util.JsonConverter;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateDeliveryFromAnOrder createDeliveryFromAnOrder;
    @MockBean
    private DeleteDelivery deleteDelivery;
    @MockBean
    private Deliver deliver;
    @MockBean
    private GetAllDeliveries getAllDeliveries;
    @MockBean
    private GetDeliveryFromCatalog getDeliveryFromCatalog;
    @MockBean
    private UpdateDelivery updateDelivery;
    @MockBean
    private JsonConverter jsonConverter;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private GetOrderFromCatalog getOrderFromCatalog;
    @MockBean
    private EmailContentCreator emailContentCreator;
    @MockBean
    private EmailService emailService;


    private final Delivery DELIVERY = Delivery.builder()
            .address("Hong Kong")
            .isDelivered(true)
            .build();

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetAllDeliveries() throws Exception {
        //given
        List<Delivery> deliveries = Collections.singletonList(DELIVERY);
        String url = "/admin/deliveries";
        //when
        Mockito.when(getAllDeliveries.getAll()).thenReturn(deliveries);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(deliveries);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetDelivery() throws Exception {
        //given

        long randomId = 9;
        String url = "/admin/deliveries/" + randomId;
        //when
        Mockito.when(getDeliveryFromCatalog.getById(any(Long.class))).thenReturn(DELIVERY);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(DELIVERY);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldDeleteDelivery() throws Exception {
        //given
        long randomId = 278;
        String url = "/admin/deliveries/" + randomId;
        //when
        Mockito.doNothing().when(deleteDelivery).deleteById(any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteDelivery, times(1)).deleteById(any(Long.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldCreateDelivery() throws Exception {
        //given
        long randomOrderId = 19;
        String randomAddress = "Privet Drive 4";
        String url = "/admin/deliveries/" + randomOrderId;
        //when
        Mockito.when(createDeliveryFromAnOrder.makeDelivery(any(Long.class), any(String.class)))
                .thenReturn(DELIVERY);
        Mockito.when(getOrderFromCatalog.getById(any(Long.class))).thenReturn(Order.builder().user(new User()).build());
        Mockito.when(emailContentCreator.getContentForUserTypePromotion(any(User.class))).thenReturn(new EmailContent());
        Mockito.doNothing().when(emailService).sendEmail(any(String.class), any(EmailContent.class));
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content(randomAddress);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(DELIVERY);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldDeliverDelivery() throws Exception {
        //given
        long randomId = 1;
        String url = "/admin/deliveries/" + randomId;
        //when
        Mockito.doNothing().when(deliver).deliverToUser(any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.put(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deliver, times(1)).deliverToUser(any(Long.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldUpdateDelivery() throws Exception {
        //given
        String deliveryJson = objectMapper.writeValueAsString(DELIVERY);
        long randomId = 1;
        String url = "/admin/deliveries/" + randomId;
        //when
        Mockito.doNothing().when(updateDelivery).update(any(Long.class), any(Delivery.class));
        Mockito.when(jsonConverter.getDeliveryFromJson(any(String.class))).thenReturn(DELIVERY);
        RequestBuilder request = MockMvcRequestBuilders
                .patch(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content(deliveryJson);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(updateDelivery, times(1))
                .update(any(Long.class), any(Delivery.class));
    }


}