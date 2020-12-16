package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;

@Component
@RequiredArgsConstructor
public class IsCopyExist {

    private final CopyRepository copyRepository;

    public boolean isExistById(Long id) {
        return copyRepository.existsById(id);
    }



}
