package com.victoree.api.services;


import com.victoree.api.domains.Sprint;
import java.util.List;

public interface SprintService {

  List<Sprint> getAll();

  List<Sprint> getSprintById(String id);
}
