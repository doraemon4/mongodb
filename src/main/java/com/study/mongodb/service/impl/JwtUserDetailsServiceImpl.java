package com.study.mongodb.service.impl;

import com.study.mongodb.dao.UserRepository;
import com.study.mongodb.factory.JwtUserFactory;
import com.study.mongodb.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author stephen
 * @Description 生成用户信息
 * @Date 10:44 2018/4/12
 */
@Service("jwtUserDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
