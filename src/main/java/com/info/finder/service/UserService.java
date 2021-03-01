package com.info.finder.service;

import com.info.finder.model.Role;
import com.info.finder.model.RoleType;
import com.info.finder.model.User;
import com.info.finder.repository.UserRepository;
import com.info.finder.service.generic.SystemCrudOperations;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements SystemCrudOperations<User, String> {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleType.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
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
