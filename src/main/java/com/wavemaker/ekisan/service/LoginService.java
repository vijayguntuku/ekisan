package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Response;
import com.wavemaker.ekisan.request.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {
    Response login(LoginRequest loginRequest, HttpSession session);
}
