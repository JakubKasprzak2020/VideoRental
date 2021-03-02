package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SortMovies {

    private final GetAllMovies getAllMovies;

    public List<Movie> sortByGenre(String nameOfGenre){
        List<Movie> movies = getAllMovies.getAllInAlphabeticalOrder();
        Genre genre = changeStringToGenre(nameOfGenre);
        return getMoviesWithGenre(movies, genre);
    }

    List<Movie> getMoviesWithGenre(List<Movie> movies, Genre genre){
       return movies.stream()
                .filter(m -> m.getGenre() == genre)
                .collect(Collectors.toList());
    }


    Genre changeStringToGenre(String nameOfGenre) {
        String nameOfGenreInUpperCase = nameOfGenre.toUpperCase();
       return Stream.of(Genre.values())
               .filter(g -> g.name().equals(nameOfGenreInUpperCase))
               .findFirst()
               .orElse(Genre.OTHERS);
        }

}
