package com.study.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    @Indexed(unique = true,direction =  IndexDirection.DESCENDING, dropDups=true)
    private String username;
    private String password;
    private String email;
    private List<String> roles;
    private Date lastPasswordResetDate;
}
