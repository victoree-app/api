package com.victoree.api.services;

import com.victoree.api.domains.Project;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProjectService {

  Page<Project> getAll(String username, int pageNum, int pageSize, String sortBy,
      Integer order)
      throws UnauthorizedRequestException;

  Project save(Project project);

  long update(String username, Project project);

  Project getOne(String username, String id);

  long delete(String username, String id);

  List<Project> getAll(String username);
}
