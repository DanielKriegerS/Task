package com.danielks.Tasks.services;

import com.danielks.Tasks.entities.Task;
import com.danielks.Tasks.models.TaskModelBasic;
import com.danielks.Tasks.models.TaskModelComplete;
import com.danielks.Tasks.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }
    public TaskModelComplete convertToModelComplete (Task task){
        return new TaskModelComplete(
                task.getId(),
                task.getHeader(),
                task.getBody(),
                task.isEnded()
        );
    }

    public Task convertToEntity (TaskModelComplete taskModelComplete){
        return new Task(
                taskModelComplete.id(),
                taskModelComplete.header(),
                taskModelComplete.body(),
                taskModelComplete.ended()
        );
    }

    public Optional<TaskModelComplete> getTaskById(Long id) throws Exception {
        Optional<Task> optionalTask = repository.findById(id);

        if(optionalTask.isPresent()){
            return optionalTask.map(this::convertToModelComplete);
        } else {
            throw new Exception("Exceção genérica");
        }
    }

    public List<TaskModelComplete> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return tasks.stream()
                .map(this::convertToModelComplete)
                .collect(Collectors.toList());
    }

    public TaskModelBasic createTask(TaskModelComplete taskModelComplete) throws Exception {
        if(taskModelComplete.body() == null || taskModelComplete.header() == null){
            throw new Exception("Exceção genérica!");
        }

        Task createdTask = convertToEntity(taskModelComplete);
        createdTask = repository.save(createdTask);
        return new TaskModelBasic(createdTask.getId());
    }

    public TaskModelBasic updateTask(Long id, TaskModelComplete updatedTask) throws Exception {
        Optional<Task> optionalTask = repository.findById(id);

        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();

            if (updatedTask.header() != null){
                existingTask.setHeader(updatedTask.header());
            }

            if (updatedTask.body() != null){
                existingTask.setBody(updatedTask.body());
            }

            repository.save(existingTask);
            return new TaskModelBasic(id);
        } else {
            throw new Exception("Exceção genérica!");
        }
    }

    public TaskModelBasic endTask(Long id) throws Exception {
        Optional<Task> endedTask = repository.findById(id);
        if (endedTask.isPresent()) {

            Task taskToEnd = endedTask.get();
            if (!taskToEnd.isEnded()) {

                taskToEnd.setEnded(true);
                repository.save(taskToEnd);
                return new TaskModelBasic(id);
            } else {
                throw new Exception("Exceção genérica - Tarafa já encerrada");
            }

        } else {
            throw new Exception("Exceção genérica");
        }
    }
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

}
