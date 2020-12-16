package pl.VideoRental.SampleData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.AddMovieToCatalog;
import pl.VideoRental.useCase.port.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.CreateUser;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SampleDataInit implements CommandLineRunner {


    private final AddMovieToCatalog addMovieToCatalog;
    private final CreateCopyOfAMovie createCopyOfAMovie;
    private final CreateUser createUser;


    @Override
    public void run(String... args) throws Exception {
        initSampleMovies();
        initSampleCopies();
        initSampleUsers();
    }


    private void initSampleMovies() throws MovieAlreadyExistException {
        Movie movie1 = Movie.builder().title("Inglourious Basterds")
                .releaseDate(LocalDate.of(2009, 1, 1))
                .build();

        Movie movie2 = Movie.builder().title("E.T.")
                .releaseDate(LocalDate.of(1982, 1, 1))
                .build();
            addMovieToCatalog.add(movie1);
            addMovieToCatalog.add(movie2);
    }

    private void initSampleCopies() throws MovieDoesNotExistException {
        createCopyOfAMovie.create(1);
        createCopyOfAMovie.create(1);
    }

    private void initSampleUsers(){
createUser.create("Quentin", "Tarantino", "password", "quentin@quentin.com", "Hollywood");
    }






}
