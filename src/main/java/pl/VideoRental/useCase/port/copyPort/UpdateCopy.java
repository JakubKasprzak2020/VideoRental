package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;

@Component
@RequiredArgsConstructor
public class UpdateCopy {

    private final GetCopyFromCatalog getCopyFromCatalog;
    private final CopyRepository copyRepository;

    public void update(long id, Copy newCopy) {
        try {
            Copy oldCopy = getCopyFromCatalog.get(id);
            oldCopy.setRentalDate(newCopy.getRentalDate());
            oldCopy.setRentalDays(newCopy.getRentalDays());
            oldCopy.setAvailable(newCopy.isAvailable());
            oldCopy.setUser(newCopy.getUser());
            oldCopy.setMovie(newCopy.getMovie());
            copyRepository.save(oldCopy);
        } catch (CopyDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }



}
