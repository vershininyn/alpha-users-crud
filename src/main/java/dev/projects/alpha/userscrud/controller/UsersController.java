package dev.projects.alpha.userscrud.controller;

import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.domain.UsersListDTO;
import dev.projects.alpha.userscrud.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        produces = "application/json")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService userService) {
        this.usersService = userService;
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<Map<String, Long>> createUser(@RequestBody UserRequestDTO newUser) {
        UserDTO user = usersService.createUser(newUser);

        return ResponseEntity.ok(Map.of("id", user.getId()));
    }

    @PutMapping(value = "/changeUser")
    public ResponseEntity<UserDTO> changeUser(@RequestBody UserDTO user) {
        UserDTO changedUser = usersService.changeUser(user);

        return ResponseEntity.ok(changedUser);
    }

    @PutMapping(value = "/banUser")
    public ResponseEntity<UserDTO> banUser(@RequestBody Map<String, String> dto) {
        UserDTO user = usersService.banUser(dto);

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<UsersListDTO> getAllUsers() {
        List<UserDTO> dtos = usersService.getAllUsers();

        return ResponseEntity.ok(new UsersListDTO(dtos));
    }

    @GetMapping(value = "/getAllUnbannedUsers")
    public ResponseEntity<UsersListDTO> getAllUnbannedUsers() {
        List<UserDTO> dtos = usersService.getAllUnbannedUsers();

        return ResponseEntity.ok(new UsersListDTO(dtos));
    }
}
