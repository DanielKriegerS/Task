package com.danielks.Tasks.exceptions.task;

import com.danielks.Tasks.exceptions.InvalidRequestException;

public class EndedTaskException extends InvalidRequestException {
    public EndedTaskException(Long id) {
        super("Task with id " + id + " is ended!");
    }
}
