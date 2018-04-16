package com.study.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author stephen
 * @Description
 * @Date 15:18 2018/4/12
 */
@Data
@AllArgsConstructor
public class JwtAuthenticationRequest {
    private String username;
    private String password;
}
