package com.gadek.logic;

import com.gadek.TaskConfigurationProperties;
import com.gadek.model.ProjectRepository;
import com.gadek.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService service(ProjectRepository repository,
                           TaskGroupRepository taskGroupService,
                           TaskConfigurationProperties config) {
        return new ProjectService(repository, taskGroupService, config);
    }
}
