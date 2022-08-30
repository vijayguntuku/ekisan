package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.User;

public interface UserDao {
      User findUser(String username, String password);
}
