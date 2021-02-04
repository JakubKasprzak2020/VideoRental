package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;
import pl.VideoRental.useCase.port.movieRatingPort.CreateMovieRating;
import pl.VideoRental.useCase.port.movieRatingPort.DeleteMovieRating;
import pl.VideoRental.useCase.port.movieRatingPort.GetAllMovieRatings;
import pl.VideoRental.useCase.port.userPort.GetUserFromCatalog;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieRatingController {


    private final CreateMovieRating createMovieRating;
    private final DeleteMovieRating deleteMovieRating;
    private final GetAllMovieRatings getAllMovieRatings;
    private final GetUserFromCatalog getUserFromCatalog;

    @GetMapping(path="/api/ratings")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieRating> getAll(){
        return getAllMovieRatings.getAll();
    }

    @DeleteMapping(path="/admin/ratings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        deleteMovieRating.delete(id);
    }

    @PostMapping(path="/api/ratings/{movieId}")
    public MovieRating create(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable long movieId,
                              @RequestBody String rating) throws MovieDoesNotExistException, UserDoesNotExistException {
        User user = getUserFromCatalog.getByEmail(userDetails.getUsername());
        int ratingInteger = Integer.parseInt(rating);
       return createMovieRating.create(user, movieId, ratingInteger);
    }


}
