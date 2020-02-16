package com.victoree.api.services.impl;

import com.victoree.api.domains.AuthSession;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.AuthenticationResponse;
import com.victoree.api.io.LoginRequest;
import com.victoree.api.repositories.AuthenticationRepository;
import com.victoree.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  AuthenticationRepository authenticationRepository;

  @Override
  public AuthenticationResponse authenticate(LoginRequest loginRequest)
      throws UnauthorizedLoginException {
    AuthSession authSession = authenticationRepository
        .authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.setSessionId(authSession.getSessionId());
    return authenticationResponse;
  }

  @Override
  public String getUserNameFromSessionId(String sessionId)
      throws UnauthorizedRequestException {
    AuthSession session = authenticationRepository.findSessionById(sessionId);
    return session.getUsername();
  }
}
