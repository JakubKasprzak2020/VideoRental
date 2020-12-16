package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMovies {

    private final MovieRepository movieRepository;

    public List<Movie> getAll(){
        List <Movie> movies = new ArrayList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        return movies;
    }


}
