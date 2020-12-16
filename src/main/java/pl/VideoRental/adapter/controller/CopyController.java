package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.port.getAllUtils.GetAllCopies;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CopyController {

    private final GetAllCopies getAllCopies;

    @GetMapping("/api/copies/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Copy> GetAllCopiesByMovieTitle(@PathVariable String title) {
        return getAllCopies.getAllByMovieTitle(title);
    }




}
