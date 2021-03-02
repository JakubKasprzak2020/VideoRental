package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllMovies {

    private final MovieRepository movieRepository;

    public List<Movie> getAll(){
        List <Movie> movies = new ArrayList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        return movies;
    }

    public List<Movie> getAllInAlphabeticalOrder(){
        List <Movie> movies = getAll();
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .collect(Collectors.toList());
    }

    public List<Movie> getAllInChronologicalOrder(){
        List <Movie> movies = getAll();
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getReleaseDate))
                .collect(Collectors.toList());
    }


}
