package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.repository.TaskRepository;
import com.dawidfrankiewicz.todo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final SecurityService securityService;

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid: {title, description, status}");
        }
    }


    @GetMapping()
    public List<Task> getTasks() {
        int userId = securityService.getAuthorizedUserId();
        return taskRepository.findAllByUser_id(userId);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) throws ResponseStatusException {
        int userId = securityService.getAuthorizedUserId();

        Task recivedTask = taskRepository.findByUser_idAndId(userId, id);

        if (recivedTask.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }

        return recivedTask;
    }

    @PostMapping()
    public void addTask(@RequestBody Task task) {
        User user = securityService.getAuthorizedUser();
        validateTask(task);
        task.setUser(user);

        taskRepository.saveAndFlush(task);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        int userId = securityService.getAuthorizedUserId();
        if (taskRepository.findByUser_idAndId(userId, id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }
        taskRepository.deleteByUser_idAndId(userId, id);
    }

    @Transactional
    @PutMapping("/{id}")
    public void editTask(@PathVariable int id, @RequestBody Task task) {
        int userId = securityService.getAuthorizedUserId();
        Task receviedTask = taskRepository.findByUser_idAndId(userId, id);
        if (task.getTitle() != null) receviedTask.setTitle(task.getTitle());
        if (task.getDescription() != null) receviedTask.setDescription(task.getDescription());
        if (task.getStatus() != null) receviedTask.setStatus(task.getStatus());


    }
}
