package cz.fel.cvut.hamrasan.gardener.rest;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/gallery")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class GalleryController {

    @GetMapping(value = "/test/{src}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String src) throws IOException {
        return IOUtils.toByteArray(getClass().getResourceAsStream("/static/images/" + src));
    }
}
