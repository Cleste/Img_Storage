package com.kirill.pimenov.controllers;

import com.kirill.pimenov.domain.GetUri;
import com.kirill.pimenov.exceptions.ImageNotFoundException;
import com.kirill.pimenov.exceptions.InvalidRequestException;
import com.kirill.pimenov.exceptions.InvalidUrlException;
import com.kirill.pimenov.services.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getImg(@PathVariable("name") String name) {
        return loadImg(name, null);
    }

    @GetMapping("/preview/{name}")
    public ResponseEntity<?> getPreviewImg(@PathVariable("name") String name) {
        return loadImg(name, "preview");
    }

    @PostMapping("/byUrl")
    public ResponseEntity<?> addImg(@RequestBody List<String> urls) {
        List<GetUri> getUris = new ArrayList<>();
        for (String url : urls) {
            GetUri getUri = saveImg(url);
            getUris.add(getUri);
        }
        if (!getUris.isEmpty())
            return ResponseEntity.ok().body(getUris);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter picture's URLs");
    }

    private ResponseEntity<?> loadImg(String name, String uploadPath) {
        try {
            return imgService.loadImg(name, uploadPath);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading image!",
                    HttpStatus.BAD_REQUEST);
        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>("Invalid image name - " + name,
                    HttpStatus.BAD_REQUEST);
        } catch (InvalidRequestException e) {
            return new ResponseEntity<>("Invalid request - " + name,
                    HttpStatus.BAD_REQUEST);
        }

    }

    private GetUri saveImg(String url) {
        try {
            return imgService.saveImg(url);
        } catch (InvalidUrlException e) {
            return new GetUri("Invalid URL - " + url);
        } catch (IOException e) {
            return new GetUri("Error loading image - " + url);
        }
    }
}
