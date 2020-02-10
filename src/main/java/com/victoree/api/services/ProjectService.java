package com.victoree.api.services;

import com.victoree.api.domains.Project;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import org.springframework.data.domain.Page;

public interface ProjectService {

  Page<Project> getAll(String userName, int pageNum, int pageSize) throws UnauthorizedRequestException;

  Project save(Project project);

  long update(String userName, Project project);

  Project getOne(String userName, String id);

  long delete(String userName, String id);
}
