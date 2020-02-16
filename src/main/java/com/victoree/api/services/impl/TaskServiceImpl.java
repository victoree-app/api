package com.victoree.api.services.impl;

import com.victoree.api.domains.Task;
import com.victoree.api.repositories.TaskRepository;
import com.victoree.api.services.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Override
  public List<Task> getAllTasks(String storyid) {
    return taskRepository.getAllTasks(storyid);
  }

  @Override
  public long delete(String taskId) {
    return taskRepository.delete(taskId);
  }

  @Override
  public List<Task> getAllTasksById(String id) {
    return taskRepository.getAllTasksById(id);
  }

  @Override
  public long update(Task task) {
    return taskRepository.update(task);
  }

  @Override
  public Task create(Task task) {
    return taskRepository.create(task);
  }
}
