package com.estore.service;

import java.util.List;

import com.estore.dto.request.UserRequest;
import com.estore.dto.response.UserResponse;
import com.estore.model.User;

public interface UserService {
    List<UserResponse> listarTodos();

    UserResponse cadastrar(UserRequest req);

    User getUsuarioAutenticado();
}
