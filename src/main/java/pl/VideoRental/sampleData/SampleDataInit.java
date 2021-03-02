package pl.VideoRental.sampleData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.authentication.ApplicationUserRole;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.movieRatingPort.CreateMovieRating;
import pl.VideoRental.useCase.port.userPort.CreateUser;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

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
    private final CreateMovieRating createMovieRating;
    private final GetAllMovies getAllMovies;
    private final GetAllUsers getAllUsers;

    @Override
    public void run(String... args) throws Exception {
        initSampleMovies();
        initSampleCopies();
        initSampleUsers();
        initAdminAccount();
        initSampleMovieRatings();
    }


    private void initSampleMovies() throws MovieAlreadyExistException {
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_1);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_2);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_3);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_4);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_5);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_6);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_7);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_8);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_9);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_10);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_11);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_12);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_13);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_14);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_15);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_16);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_17);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_18);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_19);
       createMovieAndChangeUsersSizeMarker(SampleDataStorage.MOVIE_20);
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

    private void initSampleMovieRatings() throws MovieDoesNotExistException {
        User user1 = getAllUsers.getAll().get(0);
        User user2 = getAllUsers.getAll().get(1);
        long movie1ID = getAllMovies.getAll().get(0).getId();
        long movie2ID = getAllMovies.getAll().get(1).getId();
        long movie3ID = getAllMovies.getAll().get(2).getId();
        createMovieRating.create(user1, movie1ID, 8);
        createMovieRating.create(user1, movie2ID, 6);
        createMovieRating.create(user1, movie3ID, 9);
        createMovieRating.create(user2, movie1ID, 3);
        createMovieRating.create(user2, movie2ID, 10);
        createMovieRating.create(user2, movie3ID, 5);
    }

}
