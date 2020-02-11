package com.victoree.api.services;

import com.victoree.api.domains.User;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import java.util.List;

public interface UserOpsService {

  List<User> getAllUsers(String sessionId) throws UnauthorizedRequestException;

  String getUserNameFromSessionId(String sessionId) throws UnauthorizedRequestException;
}
