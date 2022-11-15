package com.gadek.controller;

import com.gadek.model.Task;
import com.gadek.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repository;

    @Test
    void httpGet_returnAllTasks() {
        //given
        var actualSize = repository.findAll().size();
        repository.save(new Task("foo", LocalDateTime.now()));
        repository.save(new Task("bar", LocalDateTime.now()));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(actualSize + 2);
    }

}