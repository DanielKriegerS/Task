package com.danielks.Tasks.services;

import com.danielks.Tasks.entities.Task;
import com.danielks.Tasks.dtos.TaskDTO;
import com.danielks.Tasks.repositories.TaskRepository;
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

    public Optional<TaskDTO> getTaskById(Long id) throws Exception {
        Optional<Task> optionalTask = repository.findById(id);

        if(optionalTask.isPresent()){
            return optionalTask.map(this::convertToDTO);
        } else {
            throw new Exception("Exceção genérica");
        }
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws Exception {
        if(taskDTO.body() == null || taskDTO.header() == null){
            throw new Exception("Exceção genérica!");
        }

        Task createdTask = convertToEntity(taskDTO);
        createdTask = repository.save(createdTask);
        return convertToDTO(createdTask);
    }

    public TaskDTO updateTask(Long id, TaskDTO updatedTask) throws Exception {
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
            throw new Exception("Exceção genérica!");
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
