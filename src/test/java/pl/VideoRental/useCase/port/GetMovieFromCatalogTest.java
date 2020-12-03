package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetMovieFromCatalogTest {

    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    AddMovieToCatalog addMovieToCatalog;


    @Test
    public void GetMovieFromCatalogByIdWhenMovieExist() throws MovieDoesNotExist, MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Batman")
                .genre(Genre.ACTION)
                .description("Batman returned to Gotham...")
                .build();
        //when
        addMovieToCatalog.add(movie);
        Movie movieGotFromCatalog = getMovieFromCatalog.getById(movie.getId());
        //then
        assertEquals(movie.getId(), movieGotFromCatalog.getId());
    }

    @Test
    public void GetMovieFromCatalogByTitleWhenMovieExist() throws MovieDoesNotExist, MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Superman")
                .genre(Genre.ACTION)
                .description("Superman returned to Metropolis...")
                .build();
        //when
        addMovieToCatalog.add(movie);
        Movie movieGotFromCatalog = getMovieFromCatalog.getByTitle(movie.getTitle());
        //then
        assertEquals(movie.getTitle(), movieGotFromCatalog.getTitle());
    }

    @Test
    public void GetMovieFromCatalogByIdWhenMovieDoesNotExist() {
        //given
        long randomNumber = 5;
        //then
       assertThrows(MovieDoesNotExist.class, ()->{getMovieFromCatalog.getById(randomNumber);});
    }

    @Test
    public void GetMovieFromCatalogByTitleWhenMovieDoesNotExist() {
        //given
        String randomTitle = "Iron Man";
        //then
        assertThrows(MovieDoesNotExist.class, ()->{getMovieFromCatalog.getByTitle(randomTitle);});
    }


}