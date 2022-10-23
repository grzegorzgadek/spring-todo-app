package com.gadek;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    private Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template {

        private boolean allowedMultipleTasksFromTemplate;

        public boolean isAllowedMultipleTasksFromTemplate() {
            return allowedMultipleTasksFromTemplate;
        }

        public void setAllowedMultipleTasksFromTemplate(boolean allowedMultipleTasksFromTemplate) {
            this.allowedMultipleTasksFromTemplate = allowedMultipleTasksFromTemplate;
        }
    }
}
