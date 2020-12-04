package pl.VideoRental.useCase.exception;

public class CopyIsAlreadyRented extends Exception {
    public CopyIsAlreadyRented(Long id) {
        super("Copy with id " + id + "is already rented!");
    }
}
