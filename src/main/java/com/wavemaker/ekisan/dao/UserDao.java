package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.User;

public interface UserDao {
      User findUser(String email, String password);

      boolean saveOrUpdateUser(User user);

      User findUserByID(int id);
}
