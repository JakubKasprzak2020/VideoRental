package pl.VideoRental.useCase.exception;

public class CopyDoesNotExistException extends Exception {

    public CopyDoesNotExistException(Long id) {
        super(String.format("Copy with id %s doesn't exist!", id));
    }

}
