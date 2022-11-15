package com.gadek.logic;

import com.gadek.TaskConfigurationProperties;
import com.gadek.model.ProjectRepository;
import com.gadek.model.TaskGroupRepository;
import com.gadek.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(ProjectRepository repository,
                                  TaskGroupRepository taskGroupRepository,
                                  TaskConfigurationProperties config,
                                  TaskGroupService taskGroupService) {
        return new ProjectService(repository, taskGroupRepository, config, taskGroupService);
    }

    @Bean
    TaskGroupService taskGroupService(TaskGroupRepository taskGroupRepository,
                                      TaskRepository taskRepository) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}
