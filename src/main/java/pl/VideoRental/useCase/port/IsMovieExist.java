package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

@Component
@RequiredArgsConstructor
public class IsMovieExist {

    private final MovieRepository movieRepository;


    public boolean isExistByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }

    public boolean isExistById(long id) {
        return movieRepository.existsById(id);
    }


}
