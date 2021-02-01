package pl.VideoRental.useCase.exception;

public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException(Long id) {
        super("User with id " + id + " doesn't exist!");
    }
    public UserDoesNotExistException(String email) {
        super("User with email " + email + " doesn't exist!");
    }
}
