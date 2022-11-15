package com.gadek.controller;

import com.gadek.model.Task;
import com.gadek.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TaskController.class)
class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repository;

    @Test
    void httpGetReturnsGivenTasks() throws Exception {
        //given
        String desc = "foo";
        when(repository.findById(any()))
                .thenReturn(Optional.of(new Task(desc, LocalDateTime.now())));

        //expect
        mockMvc.perform(get("/tasks/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .string(containsString(desc)));
    }
}
