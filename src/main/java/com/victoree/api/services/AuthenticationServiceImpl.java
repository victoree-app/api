package com.victoree.api.services;

import com.victoree.api.domains.AuthSession;
import com.victoree.api.domains.AuthenticationResponse;
import com.victoree.api.domains.LoginRequest;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.repositories.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  AuthenticationRepository authenticationRepository;

  @Override
  public AuthenticationResponse authenticate(LoginRequest loginRequest)
      throws UnauthorizedLoginException {
    return authenticationRepository.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
  }

  @Override
  public String getUserNameFromSessionId(String sessionId) throws UnauthorizedRequestException {
    AuthSession session = authenticationRepository.findSessionById(sessionId);
    return session.getUsername();
  }
}
