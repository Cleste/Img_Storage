package com.kirill.pimenov.services;

import com.kirill.pimenov.domain.GetUrl;
import com.kirill.pimenov.exceptions.ImageNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ImgService {
    ResponseEntity<InputStreamResource> loadImg(String name, String uploadPath) throws IOException, ImageNotFoundException;
    GetUrl saveImg(String url) throws IOException;
}
