package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.request.LoginRequest;

import javax.servlet.http.HttpSession;

public interface UserService {
    Response login(LoginRequest loginRequest, HttpSession session);

    Response saveOrUpdateUser(User user);

    Response findUserById(int id);

}
