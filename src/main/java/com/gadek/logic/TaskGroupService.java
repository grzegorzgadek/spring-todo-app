package com.gadek.logic;

import com.gadek.model.TaskGroup;
import com.gadek.model.TaskGroupRepository;
import com.gadek.model.TaskRepository;
import com.gadek.model.projection.GroupReadModel;
import com.gadek.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(TaskGroupRepository repository,
                     TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup taskGroup = repository.save(source.toGroup());
        return new GroupReadModel(taskGroup);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks.");
        }
        TaskGroup taskGroup = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Task group given id not found"));
        taskGroup.setDone(!taskGroup.isDone());
        repository.save(taskGroup);
    }
}
