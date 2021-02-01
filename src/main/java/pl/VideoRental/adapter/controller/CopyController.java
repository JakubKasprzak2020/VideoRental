package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.*;
import pl.VideoRental.util.JsonConverter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CopyController {

    private final GetAllCopies getAllCopies;
    private final GetCopyFromCatalog getCopyFromCatalog;
    private final CreateCopyOfAMovie createCopyOfAMovie;
    private final DeleteCopy deleteCopy;
    private final UpdateCopy updateCopy;
    private final ReturnACopy returnACopy;
    private final RentACopy rentACopy;
    private final JsonConverter jsonConverter;

    @GetMapping("/api/copies")
    @ResponseStatus(HttpStatus.OK)
    public List<Copy> getAll(){
        return getAllCopies.getAll();
    }

    @GetMapping("/api/copies/movie/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Copy> getAllByMovieTitle(@PathVariable String title) {
        return getAllCopies.getAllByMovieTitle(title);
    }

    @GetMapping("/api/copies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Copy getById(@PathVariable long id) {
        try {
            return getCopyFromCatalog.get(id);
        } catch( CopyDoesNotExistException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @PostMapping("/admin/copies/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Copy create(@PathVariable long movieId){
        try {
           return createCopyOfAMovie.create(movieId);
        } catch (MovieDoesNotExistException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @DeleteMapping("/admin/copies/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long copyId) {
        deleteCopy.deleteById(copyId);
    }

    @PutMapping("/admin/copies/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long copyId, @RequestBody String json) {
        Copy copy = jsonConverter.getCopyFromJson(json);
        updateCopy.update(copyId, copy);
    }

    //TODO - this method looks to be redundant
    @PutMapping("/api/rent/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void rent(){

    }

    @PutMapping("/admin/return/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void returnRentedCopy(@PathVariable long copyId){
            returnACopy.returnACopyById(copyId);
    }


//TODO probably some of this methods are redundant


}
