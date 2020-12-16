package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CopyController {

    private final GetAllCopies getAllCopies;

    @GetMapping("/api/copies")
    @ResponseStatus(HttpStatus.OK)
    public List<Copy> GetAllCopies(){
        return getAllCopies.getAll();
    }

    @GetMapping("/api/copies/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Copy> GetAllCopiesByMovieTitle(@PathVariable String title) {
        return getAllCopies.getAllByMovieTitle(title);
    }




}
