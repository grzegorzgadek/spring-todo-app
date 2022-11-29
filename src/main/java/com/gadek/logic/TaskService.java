package com.gadek.logic;

import com.gadek.model.Task;
import com.gadek.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<Collection<Task>> findAllAsync() {
        logger.info("Async find!");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
