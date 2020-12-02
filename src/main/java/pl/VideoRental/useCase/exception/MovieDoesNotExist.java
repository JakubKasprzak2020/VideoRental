package pl.VideoRental.useCase.exception;

public class MovieDoesNotExist extends Exception{

    public MovieDoesNotExist(String title) {
        super(title + " does not exist!");
    }
}
