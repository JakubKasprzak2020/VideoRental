package pl.VideoRental.SampleData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.CreateUser;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SampleDataInit implements CommandLineRunner {


    /**
     * A content of this class is strongly connected with test classes.
     */

    public static int usersLengthMarker = 0;
    public static int moviesLengthMarker = 0;
    public static int copiesLengthMarker = 0;

    public static final String TITLE_OF_MOVIE_1 = "Inglourious Basterds";
    public static final String TITLE_OF_MOVIE_2 = "E.T.";
    public static final String TITLE_OF_MOVIE_3 = "Scream";

    public static final int NUMBER_OF_COPIES_OF_MOVIE_1 = 3;

    private final CreateMovie createMovie;
    private final CreateCopyOfAMovie createCopyOfAMovie;
    private final CreateUser createUser;
    private final GetMovieFromCatalog getMovieFromCatalog;


    @Override
    public void run(String... args) throws Exception {
        initSampleMovies();
        initSampleCopies();
        initSampleUsers();
    }


    private void initSampleMovies() throws MovieAlreadyExistException {
        Movie movie1 = Movie.builder()
                .title(TITLE_OF_MOVIE_1)
                .genre(Genre.ACTION)
                .description("Second World War in Europe...")
                .releaseDate(LocalDate.of(2009, 1, 1))
                .build();

        Movie movie2 = Movie.builder()
                .title(TITLE_OF_MOVIE_2)
                .genre(Genre.SCI_FI)
                .releaseDate(LocalDate.of(1982, 1, 1))
                .description("An Alien appears on the Earth...")
                .build();

        Movie movie3 = Movie.builder()
                .title(TITLE_OF_MOVIE_3)
                .genre(Genre.HORROR)
                .releaseDate(LocalDate.of(1996, 1, 1))
                .description("A masked murderer attacked!")
                .build();

        createMovieAndChangeUsersSizeMarker(movie1);
        createMovieAndChangeUsersSizeMarker(movie2);
        createMovieAndChangeUsersSizeMarker(movie3);
    }

    private void initSampleCopies() throws MovieDoesNotExistException {
        long movie1Id = getMovieFromCatalog.getByTitle(TITLE_OF_MOVIE_1).getId();
        long movie2Id = getMovieFromCatalog.getByTitle(TITLE_OF_MOVIE_2).getId();
        long movie3Id = getMovieFromCatalog.getByTitle(TITLE_OF_MOVIE_3).getId();
        for (int i = 0; i<NUMBER_OF_COPIES_OF_MOVIE_1; i++){
            createCopyAndChangeCopiesSizeMarker(movie1Id);
        }
        createCopyAndChangeCopiesSizeMarker(movie2Id);
        createCopyAndChangeCopiesSizeMarker(movie2Id);
        createCopyAndChangeCopiesSizeMarker(movie3Id);
    }

    private void initSampleUsers(){

        UserSignInData userSignInData1 = UserSignInData.builder()
                .name("Quentin")
                .lastName("Tarantino")
                .email("quentin@quentin.com")
                .password("password")
                .address("Hollywood")
                .build();
        UserSignInData userSignInData2 = UserSignInData.builder()
                .name("Queen")
                .lastName("Elizabeth")
                .email("queen@elizabeth.gb")
                .password("longlive")
                .address("London")
                .build();

        createUserAndChangeUsersSizeMarker(userSignInData1);
        createUserAndChangeUsersSizeMarker(userSignInData2);
    }




    private void createUserAndChangeUsersSizeMarker(UserSignInData userSignInData){
        createUser.create(userSignInData);
        usersLengthMarker++;
    }

    private void createMovieAndChangeUsersSizeMarker(Movie movie) throws MovieAlreadyExistException {
        createMovie.create(movie);
        moviesLengthMarker++;
    }

    private void createCopyAndChangeCopiesSizeMarker(Long id) throws MovieDoesNotExistException {
        createCopyOfAMovie.create(id);
        copiesLengthMarker++;

    }


}
