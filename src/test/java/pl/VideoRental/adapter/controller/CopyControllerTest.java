package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.SampleData.SampleDataStorage;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    private final Copy COPY_1 = Copy.builder().movie(SampleDataStorage.MOVIE_1).build();
    private final Copy COPY_2 = Copy.builder().movie(SampleDataStorage.MOVIE_1).build();
    private final Copy COPY_3 = Copy.builder().movie(SampleDataStorage.MOVIE_2).build();


    @Test
    void shouldGetAllCopies() throws Exception {
        List<Copy> copies = new ArrayList<>();
        copies.add(COPY_1);
        copies.add(COPY_2);
        copies.add(COPY_3);
        Mockito.when(getAllCopies.getAll()).thenReturn(copies);
        String url = "/api/copies";
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(copies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldGetAllCopiesWithTitle() throws Exception {
        List<Copy> copies = new ArrayList<>();
        copies.add(COPY_1);
        copies.add(COPY_2);
        String title = SampleDataStorage.MOVIE_1.getTitle();
        Mockito.when(getAllCopies.getAllByMovieTitle(title)).thenReturn(copies);
        String url = "/api/copies/" + title;
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(copies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

}