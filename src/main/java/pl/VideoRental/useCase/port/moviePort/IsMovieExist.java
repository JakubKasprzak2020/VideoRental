package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;

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
