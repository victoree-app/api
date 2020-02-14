package com.victoree.api.services.impl;


import com.victoree.api.domains.Sprint;
import com.victoree.api.repositories.SprintRepository;
import com.victoree.api.services.SprintService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintServiceImpl implements SprintService {

  @Autowired
  private SprintRepository sprintRepository;

  @Override
  public List<Sprint> getAll() {
    return sprintRepository.findAll();
  }
}
