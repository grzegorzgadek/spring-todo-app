package com.gadek.controller;

import com.gadek.model.Task;
import com.gadek.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Test
    void httpGetReturnsGivenTasks() throws Exception {
        //given
        var id = repository.save(new Task("foo", LocalDateTime.now())).getId();

        //expect
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
