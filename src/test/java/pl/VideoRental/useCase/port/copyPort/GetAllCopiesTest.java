package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.sampleData.SampleDataInit;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.sampleData.SampleDataStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllCopiesTest {

    @Autowired
    GetAllCopies getAllCopies;


    @Test
    public void shouldGetAllCopies() {
        //given
        int copiesSize = SampleDataInit.copiesLengthMarker;
        //when
        List<Copy> copies = getAllCopies.getAll();
        //then
        assertNotNull(copies);
        assertEquals(copiesSize, copies.size());
    }




    @Test
    void shouldGiveAListOfAllCopiesWithTitle() {
        //given
        String title = SampleDataStorage.MOVIE_1.getTitle();
        //when
        List<Copy> copies = getAllCopies.getAllByMovieTitle(title);
        //then
        assertEquals(SampleDataInit.NUMBER_OF_COPIES_OF_MOVIE_1, copies.size());
        assertEquals(title, copies.get(0).getMovie().getTitle());
    }

}