package com.gadek.model.projection;

import com.gadek.model.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String description;
    private Set<GroupTaskWriteModel> task;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTask() {
        return task;
    }

    public void setTask(Set<GroupTaskWriteModel> task) {
        this.task = task;
    }

    public TaskGroup toGroup() {
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(task.stream()
                .map(GroupTaskWriteModel::toTask)
                .collect(Collectors.toSet()));
        return result;
    }
}
