package com.info.finder.repository;

import com.info.finder.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(@Param("email") String email);
    User findByUsername(@Param("username") String username);
}
