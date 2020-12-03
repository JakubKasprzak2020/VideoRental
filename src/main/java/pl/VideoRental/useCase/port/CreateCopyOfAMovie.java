package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

@Component
@RequiredArgsConstructor
public class CreateCopyOfAMovie {

    private final GetMovieFromCatalog getMovieFromCatalog;
    private final CopyRepository copyRepository;


    public void create(long id) throws MovieDoesNotExist {
        Movie movie = getMovieFromCatalog.getById(id);
        Copy copy = createCopy(movie);
        copyRepository.save(copy);
    }

    private Copy createCopy(Movie movie) {
        return Copy.builder()
                .movie(movie)
                .build();
    }


}
