package pl.VideoRental.useCase.exception;

public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException(Long id) {
        super("User with id " + id + " doesn't exist!");
    }
}
