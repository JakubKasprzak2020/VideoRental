package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetCopyFromCatalog {

    private final CopyRepository copyRepository;

    public Copy get(Long id) throws CopyDoesNotExistException {
            return copyRepository.findById(id).orElseThrow(()-> new CopyDoesNotExistException(id));
    }


}
