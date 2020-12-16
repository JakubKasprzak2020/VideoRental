package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final GetAllUsers getAllUsers;

    @GetMapping("/api/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return getAllUsers.getAll();
    }




}
