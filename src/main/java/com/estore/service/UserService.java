package com.estore.service;

import java.util.List;

import com.estore.dto.request.UserRequest;
import com.estore.dto.response.UserResponse;

public interface UserService {
    List<UserResponse> listarTodos();

    UserResponse cadastrar(UserRequest req);

}
