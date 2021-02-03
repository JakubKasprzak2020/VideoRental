package pl.VideoRental.sampleData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.authentication.ApplicationUserRole;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.CreateUser;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SampleDataInit implements CommandLineRunner {


    /**
     * A content of this class is strongly connected with test classes.
     */

    public static int usersLengthMarker = 0;
    public static int moviesLengthMarker = 0;
    public static int copiesLengthMarker = 0;

    public static final String TITLE_OF_MOVIE_1 = SampleDataStorage.MOVIE_1.getTitle();
    public static final String TITLE_OF_MOVIE_2 = SampleDataStorage.MOVIE_2.getTitle();
    public static final String TITLE_OF_MOVIE_3 = SampleDataStorage.MOVIE_3.getTitle();

    public static final int NUMBER_OF_COPIES_OF_MOVIE_1 = 3;

    private final CreateMovie createMovie;
    private final CreateCopyOfAMovie createCopyOfAMovie;
    private final CreateUser createUser;
    private final GetMovieFromCatalog getMovieFromCatalog;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        initSampleMovies();
        initSampleCopies();
        initSampleUsers();
        initAdminAccount();
    }


    private void initSampleMovies() throws MovieAlreadyExistException {
        createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_1);
        createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_2);
        createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_3);
    }

    /**
    MOVIE_1 has 3 copies.
    MOVIE_2 has 2 copies.
    MOVIE_3 has 1 copy.
     */

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
        createUserAndChangeUsersSizeMarker(SampleDataStorage.USER_SIGN_IN_DATA_1);
        createUserAndChangeUsersSizeMarker(SampleDataStorage.USER_SIGN_IN_DATA_2);
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

    private void initAdminAccount(){
        ApplicationUser applicationUser = ApplicationUser
                .builder()
                .username("admin@admin.com")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList(ApplicationUserRole.ADMIN.getName()))
                .build();
        applicationUserRepository.save(applicationUser);
    }


}
