package pl.VideoRental.useCase.exception;

public class UserDoesNotExist extends Exception {

    public UserDoesNotExist(Long id) {
        super("User with id " + id + " doesn't exist!");
    }
}
