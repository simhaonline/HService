package com.hservice.controllers;

import com.hservice.domain.models.Task;
import com.hservice.exceptions.AlreadyExistsException;
import com.hservice.exceptions.NotFoundException;
import com.hservice.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task save(@RequestBody Task task) throws AlreadyExistsException {
        return taskService.save(task);
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable("id") Long id) throws NotFoundException {
        return taskService.findById(id);
    }

    @GetMapping("/by-executor")
    public List<Task> getTasksByExecutor(@RequestParam("executor") long executor) {
        return taskService.findAlByExecutor(executor);
    }

    @GetMapping("/project-table/{projectId}")
    public List<Task> getTasksByProjectId(@PathVariable("projectId") Long projectId,
                                          @RequestParam("page") int page,
                                          @RequestParam("size") int size,
                                          @RequestParam("order") boolean order,
                                          @RequestParam("parameter") String parameter) {
        return taskService.findTasksByProjectId(projectId, page, size, order, parameter);
    }
}
