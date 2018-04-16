package com.study.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author stephen
 * @Description
 * @Date 15:19 2018/4/12
 */
@AllArgsConstructor
@Data
public class JwtAuthenticationResponse {
    private final String token;
}
