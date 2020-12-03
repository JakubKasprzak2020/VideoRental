package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.CopyDoesNotExist;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

@Component
@RequiredArgsConstructor
public class GetCopyFromCatalog {

    private final CopyRepository copyRepository;

    public Copy get(Long id) throws CopyDoesNotExist {
            return copyRepository.findById(id).orElseThrow(()-> new CopyDoesNotExist(id));
    }


}
