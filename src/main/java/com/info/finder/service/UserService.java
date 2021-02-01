package com.info.finder.service;

import com.info.finder.model.User;
import com.info.finder.repository.UserRepository;
import com.info.finder.service.generic.SystemCrudOperations;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserService implements SystemCrudOperations<User, String> {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User model) {
        return userRepository.save(model);
    }

    @Override
    public User update(User model) {
        return userRepository.save(model);
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseGet(User::new);
    }

    @Override
    public void delete(String id) {
        // todo - check if current user is article owner
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> search(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
