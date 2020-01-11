package com.kirill.pimenov.services;

import com.kirill.pimenov.domain.GetUri;
import com.kirill.pimenov.exceptions.ImageNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ImgService {
    ResponseEntity<InputStreamResource> loadImg(String name, String uploadPath) throws IOException, ImageNotFoundException;
    GetUri saveImg(String url) throws IOException;
}
