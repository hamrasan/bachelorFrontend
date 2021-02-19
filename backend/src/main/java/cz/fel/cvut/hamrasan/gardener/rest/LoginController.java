package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.exceptions.AlreadyLoginException;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true", allowedHeaders = "*")
public class LoginController {

    private LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody HashMap<String,String> request) throws AlreadyLoginException {

        return service.login(request.get("email"),request.get("password"));
    }
}