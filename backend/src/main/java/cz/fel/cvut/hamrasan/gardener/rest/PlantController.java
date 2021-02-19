package cz.fel.cvut.hamrasan.gardener.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plants")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class PlantController {


}
