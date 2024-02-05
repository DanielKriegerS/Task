package com.danielks.Tasks.exceptions.task;

import com.danielks.Tasks.exceptions.EntityNotFoundException;

public class InvalidTaskRequestException extends EntityNotFoundException {
    public InvalidTaskRequestException(String invalidArgument) {
        super("The argument " + invalidArgument + " is invalid!");
    }
}
