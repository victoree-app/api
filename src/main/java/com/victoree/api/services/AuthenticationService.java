package com.victoree.api.services;

import com.victoree.api.domains.AuthenticationResponse;
import com.victoree.api.domains.LoginRequest;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;

public interface AuthenticationService {

  AuthenticationResponse authenticate(LoginRequest loginRequest) throws UnauthorizedLoginException;

  String getUserNameFromSessionId(String sessionId) throws UnauthorizedRequestException;
}
