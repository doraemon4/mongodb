package com.study.mongodb.dao;

import com.study.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author stephen
 * @Description
 * @Date 10:45 2018/4/12
 */
public interface UserRepository extends MongoRepository<User,String>{
    User findByUsername(String Username);
}
