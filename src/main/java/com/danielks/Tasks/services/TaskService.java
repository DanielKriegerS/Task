package com.danielks.Tasks.services;

import com.danielks.Tasks.dtos.mappers.TaskMapper;
import com.danielks.Tasks.entities.Task;
import com.danielks.Tasks.dtos.TaskDTO;
import com.danielks.Tasks.exceptions.task.EndedTaskException;
import com.danielks.Tasks.exceptions.task.InvalidTaskRequestException;
import com.danielks.Tasks.exceptions.task.TaskNotFoundException;
import com.danielks.Tasks.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        Optional<Task> optionalTask = repository.findById(id);

        if(optionalTask.isPresent()){
            return optionalTask.map(mapper.INSTANCE::taskToTaskDTO);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return tasks.stream()
                .map(mapper.INSTANCE::taskToTaskDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        if(taskDTO.body() == null){
            throw new InvalidTaskRequestException("body");
        }
        if (taskDTO.header() == null){
            throw new InvalidTaskRequestException("header");
        }

        Task createdTask = mapper.INSTANCE.taskDTOToTask(taskDTO);
        createdTask = repository.save(createdTask);
        return mapper.INSTANCE.taskToTaskDTO(createdTask);
    }

    public TaskDTO updateTask(Long id, TaskDTO updatedTask) {
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
            return mapper.INSTANCE.taskToTaskDTO(existingTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDTO endTask(Long id) {
        Optional<Task> endedTask = repository.findById(id);
        if (endedTask.isPresent()) {

            Task taskToEnd = endedTask.get();
            if (!taskToEnd.isEnded()) {

                taskToEnd.setEnded(true);
                repository.save(taskToEnd);
                return mapper.INSTANCE.taskToTaskDTO(taskToEnd);
            } else {
                throw new EndedTaskException(id);
            }
        } else {
            throw new TaskNotFoundException(id);
        }
    }
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

}
