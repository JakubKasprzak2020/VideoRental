package pl.VideoRental.useCase.port.moviePort;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.copyPort.IsCopyFree;

import java.util.List;

@Component
@AllArgsConstructor
public class IsMovieAvailable {


    private final GetAllCopies getAllCopies;
    private final IsCopyFree isCopyFree;

    public boolean isAvailable(long id){
        List<Copy> freeCopiesOfMovie = getAllCopies.getAll();
        freeCopiesOfMovie.removeIf(c -> c.getMovie().getId() != id || !isCopyFree.isFree(c));
                return !freeCopiesOfMovie.isEmpty();
    }

}
