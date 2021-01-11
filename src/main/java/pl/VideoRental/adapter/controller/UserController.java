package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;
import pl.VideoRental.useCase.port.userPort.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final GetAllUsers getAllUsers;
    private final GetUserFromCatalog getUserFromCatalog;
    private final CreateUser createUser;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;

    @GetMapping("/api/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll(){
        return getAllUsers.getAll();
    }

    @GetMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable long id){
        try {
           return getUserFromCatalog.getById(id);
        }
        catch (UserDoesNotExistException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserSignInData userSignInData) {
        return createUser.create(userSignInData);
    }

    @DeleteMapping("api/users/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        deleteUser.deleteById(id);
    }

    @PutMapping(value = "api/users/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody User user, @PathVariable long id) {
        updateUser.update(id, user);
    }


}
