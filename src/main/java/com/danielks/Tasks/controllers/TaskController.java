package com.danielks.Tasks.controllers;

import com.danielks.Tasks.models.TaskModelBasic;
import com.danielks.Tasks.models.TaskModelComplete;
import com.danielks.Tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskModelComplete>> getAllTasks(){
        List<TaskModelComplete> tasks = service.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModelComplete> getTaskById(@PathVariable Long id) throws Exception {
        Optional<TaskModelComplete> task = service.getTaskById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TaskModelBasic> createTask(@RequestBody TaskModelComplete taskModelComplete) throws Exception {
        TaskModelBasic createdTask = service.createTask(taskModelComplete);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModelBasic> updateTask(@PathVariable Long id,
                                                     @RequestBody TaskModelComplete taskModelComplete) throws Exception {

        TaskModelBasic updatedTask = service.updateTask(id, taskModelComplete);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("end/{id}")
    public ResponseEntity<TaskModelBasic> endTask(@PathVariable Long id) throws Exception {
        TaskModelBasic endedTask = service.endTask(id);
        if (endedTask != null) {
            return new ResponseEntity<>(endedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskModelBasic> deleteTask(@PathVariable Long id){
        service.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
