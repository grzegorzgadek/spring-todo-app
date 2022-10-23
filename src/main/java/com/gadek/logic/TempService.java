package com.gadek.logic;

import com.gadek.model.Task;
import com.gadek.model.TaskGroup;
import com.gadek.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempService {

    @Autowired
    List<String> temp(TaskGroupRepository repository) {
        //fixme n+1
        return repository.findAll()
                .stream()
                .map(TaskGroup::getTasks)
                .flatMap(Collection::stream)
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
}
