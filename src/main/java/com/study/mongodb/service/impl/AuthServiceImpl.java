package com.study.mongodb.service.impl;

import com.study.mongodb.dao.UserRepository;
import com.study.mongodb.model.JwtUser;
import com.study.mongodb.model.User;
import com.study.mongodb.service.AuthService;
import com.study.mongodb.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author stephen
 * @Description 认证
 * @Date 14:29 2018/4/12
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public User register(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());
        user.setRoles(Arrays.asList("ROLE_USER"));
        return userRepository.insert(user);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
