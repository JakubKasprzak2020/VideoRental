package pl.VideoRental.useCase.exception;

public class MovieAlreadyExistException extends Exception {

    public MovieAlreadyExistException(String title) {
        super(title + " already exists in catalog!");
    }
}
