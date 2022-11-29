package com.gadek.controller;

import com.gadek.logic.TaskService;
import com.gadek.model.Task;
import com.gadek.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    private final TaskService taskService;

    public TaskController(final TaskRepository repository, TaskService taskService) {
        this.repository = repository;
        this.taskService = taskService;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<Collection<Task>>> readAllTasks() {
        LOGGER.warn("Exposing all the tasks");
        return taskService.findAllAsync().thenApply(ResponseEntity::ok);
//        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        LOGGER.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (repository.existsById(id)) {
            repository.findById(id)
                    .ifPresent(task -> {
                        task.updateFrom(toUpdate);
                        repository.save(task);
                    });
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> getById(@PathVariable int id) {
        Optional<Task> task = repository.findById(id);
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.findById(id)
                    .ifPresent(task -> task.setDone(!task.isDone()));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/done")
    public ResponseEntity<Collection<Task>> searchByDone(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDone(state));
    }
}
