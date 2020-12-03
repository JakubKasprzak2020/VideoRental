package pl.VideoRental.useCase.exception;

public class CopyDoesNotExist extends Exception {

    public CopyDoesNotExist(Long id) {
        super("Copy with id " + id + " doesn't exist!");
    }
}
