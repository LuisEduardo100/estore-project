package com.estore.service;

import com.estore.dto.request.AuthRequest;
import com.estore.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
