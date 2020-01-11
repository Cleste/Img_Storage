package com.kirill.pimenov.services.implementations;


import com.kirill.pimenov.domain.Image;
import com.kirill.pimenov.repositories.ImageRepository;
import com.kirill.pimenov.services.ImgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/dbtest/create_img_before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/dbtest/delete_img_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ImgServiceImlTest {


    @Autowired
    private ImgService imgService;

    @MockBean
    private ImageRepository imageRepository;

    @Test
    public void loadImg() throws IOException {
        String fileName = "test.jpg";
        Image testImage = new Image(fileName);
        when(imageRepository.findByName(fileName)).thenReturn(testImage);
        imgService.loadImg(fileName, null);
        verify(imageRepository, times(1))
                .findByName(fileName);
    }

    @Test
    public void saveImg() throws IOException {
        String testUrl = "https://devrahababa.com/wp-content/uploads/2018/01/full-moon-1.jpg";

        imgService.saveImg(testUrl);

        verify(imageRepository, times(1))
                .save(ArgumentMatchers.any(Image.class));
    }
}