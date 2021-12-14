package hr.fer.apuw.lab1.services;

import hr.fer.apuw.lab1.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    User findById(Long id);
    void deleteById(Long id);
    User createOrUpdateUser(Long id, User user);
    User findByUsername(String username);
}
