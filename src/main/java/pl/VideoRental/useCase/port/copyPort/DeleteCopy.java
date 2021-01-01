package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteCopy {

    private final CopyRepository copyRepository;
    private final GetCopyFromCatalog getCopyFromCatalog;
    private final MovieRepository movieRepository;

    public void deleteById(long id){
        try {
            Copy copy = getCopyFromCatalog.get(id);
            Movie movie = copy.getMovie();
            movie.getCopies().remove(copy); // copy would be removed from data base because of annotation in Movie class
            movieRepository.save(movie);
        } catch (CopyDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }


}
