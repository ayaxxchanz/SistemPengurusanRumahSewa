package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
