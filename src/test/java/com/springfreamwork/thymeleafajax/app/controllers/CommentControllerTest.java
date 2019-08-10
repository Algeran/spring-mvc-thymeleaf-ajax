package com.springfreamwork.thymeleafajax.app.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void commentControllerShouldCallViewWithAllGenresAndFillModel() throws Exception {

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("allComments"));

    }

    @Test
    public void commentControllerShouldReturnViewWithNameCreateComment() throws Exception {


        mockMvc.perform(get("/createComment"))
                .andExpect(status().isOk())
                .andExpect(view().name("createComment"))
        ;

    }

    @Test
    public void commentControllerShouldReturnEditBookPageWithDataInModel() throws Exception {

        mockMvc.perform(get("/editComment"))
                .andExpect(status().isOk())
                .andExpect(view().name("editComment"));
    }

}