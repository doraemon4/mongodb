package com.study.mongodb.service;

import com.study.mongodb.model.User;

/**
 * @Author stephen
 * @Description
 * @Date 14:28 2018/4/12
 */
public interface AuthService {
    User register(User user);
    String login(String username, String password);
    String refresh(String oldToken);
}
