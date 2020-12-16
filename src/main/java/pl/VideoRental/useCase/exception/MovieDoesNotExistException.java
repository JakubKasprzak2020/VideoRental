package pl.VideoRental.useCase.exception;

public class MovieDoesNotExistException extends Exception{

    public MovieDoesNotExistException(String title) {
        super(title + " does not exist!");
    }
}
