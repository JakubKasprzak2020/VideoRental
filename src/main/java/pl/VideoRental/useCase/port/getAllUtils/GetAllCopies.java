package pl.VideoRental.useCase.port.getAllUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllCopies {

    @Autowired
    CopyRepository copyRepository;

    public List<Copy> getAll(){
        List <Copy> copies = new ArrayList<>();
        copyRepository.findAll().iterator().forEachRemaining(copies::add);
        return copies;
    }

    public List<Copy> getAllWithTitle(String title){
        List <Copy> copies = getAll();
        return copies.stream().filter(copy -> copy.getMovie().getTitle().equals(title)).collect(Collectors.toList());
    }

}
