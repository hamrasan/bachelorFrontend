package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.RequestWrapper;
import cz.fel.cvut.hamrasan.gardener.dto.UserDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.BadPassword;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.exceptions.UnauthorizedException;
import cz.fel.cvut.hamrasan.gardener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true", allowedHeaders = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    public UserDto getCurrent() throws UnauthorizedException {
        return userService.getCurrentUser();
    }

    @GetMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE )
    public UserDto getUserByID(@PathVariable Long id) {
        return userService.find(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody RequestWrapper requestWrapper) throws BadPassword, NotAllowedException {
        userService.createUser(requestWrapper.getUser(), requestWrapper.getPassword_control());
    }
}
