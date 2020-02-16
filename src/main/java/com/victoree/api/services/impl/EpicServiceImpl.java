package com.victoree.api.services.impl;

import com.victoree.api.domains.Epic;
import com.victoree.api.repositories.EpicRepository;
import com.victoree.api.services.EpicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EpicServiceImpl implements EpicService {

  @Autowired
  private EpicRepository epicRepository;

  @Override
  public Page<Epic> getAllEpics(String projectId, PageRequest of) {
    return epicRepository.findAll(projectId, of);
  }

  @Override
  public Epic saveEpic(Epic epic) {
    return epicRepository.createEpic(epic);
  }

  @Override
  public Epic findOne(String id) {
    return epicRepository.findOne(id);
  }

  @Override
  public long update(Epic epic) {
    return epicRepository.updateEpic(epic);
  }

  @Override
  public long delete(String id) {
    return epicRepository.delete(id);
  }

  @Override
  public List<Epic> getAllNoFilter() {
    return epicRepository.findAll();
  }
}
