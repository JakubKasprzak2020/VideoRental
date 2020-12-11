package pl.VideoRental.adapter.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.getAllUtils.GetAllUsers;

import java.util.List;

@RestController
@AllArgsConstructor
public class GetAllUsersController {

    private final GetAllUsers getAllUsers;

    @GetMapping("/api/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return getAllUsers.getAll();
    }


}
