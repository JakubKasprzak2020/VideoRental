package pl.VideoRental.useCase.exception;

public class CopyIsAlreadyRentedException extends Exception {
    public CopyIsAlreadyRentedException(Long id) {
        super("Copy with id " + id + "is already rented!");
    }
}
