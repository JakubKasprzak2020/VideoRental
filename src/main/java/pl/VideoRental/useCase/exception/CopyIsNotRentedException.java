package pl.VideoRental.useCase.exception;

public class CopyIsNotRentedException extends Exception {

    public CopyIsNotRentedException(long id) {
        super(String.format("Copy of id %s is not rented.", id));
    }
}
