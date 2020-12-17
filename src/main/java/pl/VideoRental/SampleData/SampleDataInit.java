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
import pl.VideoRental.useCase.port.userPort.CreateUser;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SampleDataInit implements CommandLineRunner {

    public static int usersLengthMarker = 0;
    public static int moviesLengthMarker = 0;

    private final CreateMovie createMovie;
    private final CreateCopyOfAMovie createCopyOfAMovie;
    private final CreateUser createUser;


    @Override
    public void run(String... args) throws Exception {
        initSampleMovies();
        initSampleCopies();
        initSampleUsers();
    }


    private void initSampleMovies() throws MovieAlreadyExistException {
        Movie movie1 = Movie.builder()
                .title("Inglourious Basterds")
                .genre(Genre.ACTION)
                .description("Second World War in Europe...")
                .releaseDate(LocalDate.of(2009, 1, 1))
                .build();

        Movie movie2 = Movie.builder()
                .title("E.T.")
                .genre(Genre.SCI_FI)
                .releaseDate(LocalDate.of(1982, 1, 1))
                .description("An Alien appears on the Earth...")
                .build();

        Movie movie3 = Movie.builder()
                .title("Scream")
                .genre(Genre.HORROR)
                .releaseDate(LocalDate.of(1996, 1, 1))
                .description("A masked murderer attacked!")
                .build();

        createMovieAndChangeUsersSizeMarker(movie1);
        createMovieAndChangeUsersSizeMarker(movie2);
        createMovieAndChangeUsersSizeMarker(movie3);
    }

    private void initSampleCopies() throws MovieDoesNotExistException {
        createCopyOfAMovie.create(1);
        createCopyOfAMovie.create(1);
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


}
