package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.sampleData.SampleDataInit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SortMoviesTest {

    @Autowired
    SortMovies sortMovies;

    public static final Movie MOVIE_1 = Movie.builder()
            .title("Western1")
            .genre(Genre.WESTERN)
            .build();

    public static final Movie MOVIE_2 = Movie.builder()
            .title("Western2")
            .genre(Genre.WESTERN)
            .build();

    public static final Movie MOVIE_3 = Movie.builder()
            .title("Comedy1")
            .genre(Genre.COMEDY)
            .build();

    public static final Movie MOVIE_4 = Movie.builder()
            .title("Comedy2")
            .genre(Genre.COMEDY)
            .build();

    public static final Movie MOVIE_5 = Movie.builder()
            .title("Western3")
            .genre(Genre.WESTERN)
            .build();

    List<Movie> movies = Arrays.asList(MOVIE_1, MOVIE_2, MOVIE_3, MOVIE_4, MOVIE_5);

    @Test
    void sortByGenre() {
        //given
        String comedyName = Genre.COMEDY.name();
        //when
        List<Movie> result = sortMovies.sortByGenre(comedyName);
        //then
        assertNotEquals(SampleDataInit.moviesLengthMarker, result.size());
        assertEquals(result.get(0).getGenre(), Genre.COMEDY);
        assertEquals(result.get(result.size()-1).getGenre(), Genre.COMEDY);
    }

    @ParameterizedTest
    @MethodSource("providesGenreNameWithGenre")
    void shouldChangeStringToGenre(String genreName, Genre expected) {
        //when
        Genre result = sortMovies.changeStringToGenre(genreName);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> providesGenreNameWithGenre() {
        return Stream.of(
                Arguments.of(Genre.ACTION.name(), Genre.ACTION),
                Arguments.of(Genre.COMEDY.name(), Genre.COMEDY),
                Arguments.of(Genre.CRIME_STORY.name(), Genre.CRIME_STORY),
                Arguments.of(Genre.DRAMA.name(), Genre.DRAMA),
                Arguments.of(Genre.WESTERN.name(), Genre.WESTERN),
                Arguments.of(Genre.HISTORICAL.name(), Genre.HISTORICAL),
                Arguments.of(Genre.HORROR.name(), Genre.HORROR),
                Arguments.of(Genre.CARTOON.name(), Genre.CARTOON),
                Arguments.of(Genre.SCI_FI.name(), Genre.SCI_FI),
                Arguments.of(Genre.MUSICAL.name(), Genre.MUSICAL),
                Arguments.of(Genre.OTHERS.name(), Genre.OTHERS)
        );
    }

    @Test
    void shouldGetMoviesWithGenre() {
        //given
        Genre western = Genre.WESTERN;
        int numberOfWesterns = 3;
        //when
        List<Movie> result = sortMovies.getMoviesWithGenre(movies, western);
        // then
        assertEquals(numberOfWesterns, result.size());
    }

}