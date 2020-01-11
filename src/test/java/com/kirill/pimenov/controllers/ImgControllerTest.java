package com.kirill.pimenov.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/dbtest/create_img_before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/dbtest/delete_img_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ImgControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getImg() throws Exception{
        this.mockMvc.perform(get("/img/test.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andDo(print());
    }

    @Test
    public void getPreviewImg() throws Exception{
        this.mockMvc.perform(get("/img/preview/test.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andDo(print());
     }

    @Test
    public void addImg() throws Exception{
        this.mockMvc.perform(post("/img/byUrl").contentType(MediaType.APPLICATION_JSON)
                .content("[\"https://devrahababa.com/wp-content/uploads/2018/01/full-moon-1.jpg\"]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }
}