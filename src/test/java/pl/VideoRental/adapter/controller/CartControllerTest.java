package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.authentication.UserDetailsServiceImpl;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserType;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.port.cartPort.AddCopyToCart;
import pl.VideoRental.useCase.port.cartPort.EmptyACart;
import pl.VideoRental.useCase.port.cartPort.GetCart;
import pl.VideoRental.useCase.port.cartPort.RemoveCopyFromCart;
import pl.VideoRental.useCase.port.copyPort.GetCopyFromCatalog;
import pl.VideoRental.useCase.port.userPort.GetUserFromCatalog;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddCopyToCart addCopyToCart;
    @MockBean
    private EmptyACart emptyACart;
    @MockBean
    private RemoveCopyFromCart removeCopyFromCart;
    @MockBean
    private GetCart getCart;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private GetCopyFromCatalog getCopyFromCatalog;
    @MockBean
    private GetUserFromCatalog getUserFromCatalog;
    @MockBean
    private ApplicationUserRepository applicationUserRepository;

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldGetCart() throws Exception {
        //given
        String url = "/api/cart";
        //when
        Mockito.when(getCart.get()).thenReturn(new Cart());
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        Mockito.verify(getCart, times(1)).get();
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldEmptyCart() throws Exception {
        //given
        String url = "/api/cart";
        //when
        Mockito.doNothing().when(emptyACart).empty();
        RequestBuilder request = MockMvcRequestBuilders.patch(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        Mockito.verify(emptyACart, times(1)).empty();
    }

    //problem with testing authenticationPrincipal
    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldAddCopyToCart() throws Exception {
        //given
        String url = "/api/cart/in/62";
        ApplicationUser applicationUser = new ApplicationUser();
        User user = User.builder().userType(UserType.REGULAR).build();
        Copy copy = new Copy();
        //when
        Mockito.when(applicationUserRepository.findByUsername(any(String.class))).thenReturn(applicationUser);
        Mockito.when(getUserFromCatalog.getByApplicationUser(any(ApplicationUser.class))).thenReturn(user);
        Mockito.when(getCopyFromCatalog.get(any(Long.class))).thenReturn(copy);
        RequestBuilder request = MockMvcRequestBuilders.put(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content("3");
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(getUserFromCatalog, times(1)).getByApplicationUser(any(ApplicationUser.class));
        Mockito.verify(getCopyFromCatalog, times(1)).get(any(Long.class));
        Mockito.verify(addCopyToCart, times(1))
                .add(any(User.class), any(Copy.class), any(Integer.class), any(LocalDate.class));
    }

}