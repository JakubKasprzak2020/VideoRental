package pl.VideoRental.useCase.exception;

public class MovieIsNotAvailableException extends Exception{
    public MovieIsNotAvailableException(long id) {
        super(String.format("There is no free copy of movie with id %s.", id));
    }
}
