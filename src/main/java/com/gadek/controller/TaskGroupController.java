package com.gadek.controller;

import com.gadek.logic.TaskGroupService;
import com.gadek.model.Task;
import com.gadek.model.TaskGroup;
import com.gadek.model.TaskRepository;
import com.gadek.model.projection.GroupReadModel;
import com.gadek.model.projection.GroupTaskWriteModel;
import com.gadek.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskGroupService taskGroupService;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskGroupController(TaskGroupService taskGroupService, TaskRepository taskRepository) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity<GroupReadModel> createTaskGroup(@RequestBody @Valid GroupWriteModel writeModel) {
        GroupReadModel readModel = taskGroupService.createGroup(writeModel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + readModel.getId()).build().toUri();
        return ResponseEntity.created(uri).body(readModel);
    }

    @GetMapping
    public ResponseEntity<List<GroupReadModel>> getAllTaskGroup() {
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTaskFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
