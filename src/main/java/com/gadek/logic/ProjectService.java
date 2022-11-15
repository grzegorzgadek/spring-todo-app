package com.gadek.logic;

import com.gadek.TaskConfigurationProperties;
import com.gadek.model.*;
import com.gadek.model.projection.GroupReadModel;
import com.gadek.model.projection.GroupTaskWriteModel;
import com.gadek.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service //todo usuniete bo tworzenie beana do LogicConfiguraion przeniesione
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config, TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.taskGroupService = taskGroupService;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowedMultipleTasksFromTemplate() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }

        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTask(project.getSteps().stream()
                            .map(step -> {
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(step.getDescription());
                                        task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                        return task;
                                    }
                            ).collect(Collectors.toSet()));
                    return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Procjet with given id not found"));
    }
}
