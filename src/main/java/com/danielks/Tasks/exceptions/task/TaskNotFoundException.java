package com.danielks.Tasks.exceptions.task;

import com.danielks.Tasks.exceptions.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long id) {
        super("Task with id " + id + " not founded!");
    }
}
