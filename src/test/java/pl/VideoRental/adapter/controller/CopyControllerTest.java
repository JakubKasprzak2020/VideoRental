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
import pl.VideoRental.sampleData.SampleDataStorage;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.port.copyPort.*;
import pl.VideoRental.util.JsonConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CopyController.class)
class CopyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    GetAllCopies getAllCopies;
    @MockBean
    private GetCopyFromCatalog getCopyFromCatalog;
    @MockBean
    private CreateCopyOfAMovie createCopyOfAMovie;
    @MockBean
    private DeleteCopy deleteCopy;
    @MockBean
    private UpdateCopy updateCopy;
    @MockBean
    private ReturnACopy returnACopy;
    @MockBean
    private JsonConverter jsonConverter;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private final Copy COPY_1 = Copy.builder().movie(SampleDataStorage.MOVIE_1).build();
    private final Copy COPY_2 = Copy.builder().movie(SampleDataStorage.MOVIE_1).build();
    private final Copy COPY_3 = Copy.builder().movie(SampleDataStorage.MOVIE_2).build();


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetAllCopies() throws Exception {
        //given
        List<Copy> copies = new ArrayList<>();
        copies.add(COPY_1);
        copies.add(COPY_2);
        copies.add(COPY_3);
        String url = "/admin/copies";
        //when
        Mockito.when(getAllCopies.getAll()).thenReturn(copies);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(copies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetAllCopiesWithTitle() throws Exception {
        //given
        List<Copy> copies = new ArrayList<>();
        copies.add(COPY_1);
        copies.add(COPY_2);
        String title = SampleDataStorage.MOVIE_1.getTitle();
        String url = "/admin/copies/movie/" + title;
        //when
        Mockito.when(getAllCopies.getAllByMovieTitle(title)).thenReturn(copies);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(copies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetCopy() throws Exception {
        //given
        long randomId = 6;
        String url = "/admin/copies/" + randomId;
        //when
        Mockito.when(getCopyFromCatalog.get(any(Long.class))).thenReturn(COPY_1);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(COPY_1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldCreateCopy() throws Exception {
        //given
        long randomId = 13;
        String url = "/admin/copies/" + randomId;
        //when
        Mockito.when(createCopyOfAMovie.create(any(Long.class))).thenReturn(COPY_1);
        RequestBuilder request = MockMvcRequestBuilders.post(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(COPY_1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldDeleteCopy() throws Exception {
        //given
        long randomId = 9;
        String url = "/admin/copies/" + randomId;
        //when
        Mockito.doNothing().when(deleteCopy).deleteById(any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteCopy, times(1)).deleteById(any(Long.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldUpdateCopy() throws Exception {
        //given
        long randomId = 1;
        String url = "/admin/copies/" + randomId;
        //when
        Mockito.doNothing().when(updateCopy).update(any(Long.class), any(Copy.class));
        Mockito.when(jsonConverter.getCopyFromJson(any(String.class))).thenReturn(COPY_1);
        RequestBuilder request = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(COPY_1));
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(updateCopy, times(1))
                .update(any(Long.class), any(Copy.class));
    }


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldReturnRentedCopy() throws Exception {
        //given
        long randomId = 1;
        String url = "/admin/return/" + randomId;
        //when
        Mockito.doNothing().when(returnACopy).returnACopyById(any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.put(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(returnACopy, times(1)).returnACopyById(any(Long.class));
    }

}