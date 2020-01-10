package com.kirill.pimenov.controllers;

import com.kirill.pimenov.domain.GetUrl;
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
    public ResponseEntity<?> getSmallImg(@PathVariable("name") String name) {
        return loadImg(name, "preview");
    }

    @PostMapping("/byUrl")
    public ResponseEntity<?> addImg(@RequestBody List<String> urls) {
        List<GetUrl> getUrls = new ArrayList<>();
        for (String url : urls) {
            GetUrl getUrl = saveImg(url);
            getUrls.add(getUrl);
        }
        if (!getUrls.isEmpty())
            return ResponseEntity.ok().body(getUrls);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter picture's URLs");
    }

    private ResponseEntity<?> loadImg(String name, String uploadPath) {
        try {
            return imgService.loadImg(name, uploadPath);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading image!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    private GetUrl saveImg(String url) {
        try {
            return imgService.saveImg(url);
        } catch (InvalidUrlException e) {
            return new GetUrl("Invalid URL - " + url);
        } catch (IOException e) {
            return new GetUrl("Error loading image - " + url);
        }
    }
}
