package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.MovieIsNotAvailableException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.copyPort.IsCopyFree;
import pl.VideoRental.useCase.port.moviePort.IsMovieAvailable;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AddMovieToCart {

    private final IsMovieAvailable isMovieAvailable;
    private final AddCopyToCart addCopyToCart;
    private final GetAllCopies getAllCopies;
    private final IsCopyFree isCopyFree;

    public void add(User user, long movieId, int rentalDays) throws MovieIsNotAvailableException, CopyIsAlreadyRentedException {
        Copy copy = getFreeCopyOfMovie(movieId);
        LocalDate now = LocalDate.now();
        addCopyToCart.add(user, copy, rentalDays, now);
    }


    private Copy getFreeCopyOfMovie(long movieId) throws MovieIsNotAvailableException {
        if (!isMovieAvailable.isAvailable(movieId)) {
            throw new MovieIsNotAvailableException(movieId);
        } else {
            return getAllCopies.getAll().stream()
                    .filter(c -> c.getMovie().getId() == movieId && isCopyFree.isFree(c))
                    .findFirst()
                    .orElse(null);
        }
    }


}
