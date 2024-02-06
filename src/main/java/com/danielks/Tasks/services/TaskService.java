package com.danielks.Tasks.services;

import com.danielks.Tasks.dtos.mappers.TaskMapper;
import com.danielks.Tasks.entities.Task;
import com.danielks.Tasks.dtos.TaskDTO;
import com.danielks.Tasks.exceptions.InvalidRequestException;
import com.danielks.Tasks.exceptions.task.EndedTaskException;
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
    public TaskDTO convertToDTO(Task task){
        return new TaskDTO(
                task.getId(),
                task.getHeader(),
                task.getBody(),
                task.isEnded()
        );
    }

    public Task convertToEntity (TaskDTO taskDTO){
        return new Task(
                taskDTO.id(),
                taskDTO.header(),
                taskDTO.body(),
                taskDTO.ended()
        );
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        Optional<Task> optionalTask = repository.findById(id);

        if(optionalTask.isPresent()){
            return optionalTask.map(this::convertToDTO);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        if(taskDTO.body() == null){
            throw new InvalidRequestException("body");
        }
        if (taskDTO.header() == null){
            throw new InvalidRequestException("header");
        }

        Task createdTask = convertToEntity(taskDTO);
        createdTask = repository.save(createdTask);
        return convertToDTO(createdTask);
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
            return convertToDTO(existingTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDTO endTask(Long id) throws Exception {
        Optional<Task> endedTask = repository.findById(id);
        if (endedTask.isPresent()) {

            Task taskToEnd = endedTask.get();
            if (!taskToEnd.isEnded()) {

                taskToEnd.setEnded(true);
                repository.save(taskToEnd);
                return convertToDTO(taskToEnd);
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
