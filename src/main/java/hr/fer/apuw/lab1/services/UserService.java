package hr.fer.apuw.lab1.services;

import hr.fer.apuw.lab1.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    User findById(Long id);
    void deleteById(Long id,  org.springframework.security.core.userdetails.User user);
    User updateUser(Long id, User user, org.springframework.security.core.userdetails.User secUser);
    User findByUsername(String username);
}
