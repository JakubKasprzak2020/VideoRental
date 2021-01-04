package pl.VideoRental.SampleData;

import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.UserSignInData;

import java.time.LocalDate;

public class SampleDataStorage {

    public static final Movie MOVIE_1 = Movie.builder()
            .title("Inglourious Basterds")
            .genre(Genre.ACTION)
            .description("Second World War in Europe...")
            .releaseDate(LocalDate.of(2009, 1, 1))
            .build();

    public static final Movie MOVIE_2 = Movie.builder()
            .title("E.T.")
            .genre(Genre.SCI_FI)
            .releaseDate(LocalDate.of(1982, 1, 1))
            .description("An Alien appears on the Earth...")
            .build();

    public static final Movie MOVIE_3 = Movie.builder()
            .title("Scream")
            .genre(Genre.HORROR)
            .releaseDate(LocalDate.of(1996, 1, 1))
            .description("A masked murderer attacked!")
            .build();


   public static final UserSignInData USER_SIGN_IN_DATA_1 = UserSignInData.builder()
            .name("Quentin")
            .lastName("Tarantino")
            .email("quentin@quentin.com")
            .password("password")
            .address("Hollywood")
            .build();

    public static final UserSignInData USER_SIGN_IN_DATA_2 = UserSignInData.builder()
            .name("Queen")
            .lastName("Elizabeth")
            .email("queen@elizabeth.gb")
            .password("longlive")
            .address("London")
            .build();



}
