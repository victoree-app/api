package com.victoree.api.services;

import com.victoree.api.domains.Task;
import java.util.List;

public interface TaskService {

  List<Task> getAllTasks(String storyid);

  long delete(String taskId);
}
