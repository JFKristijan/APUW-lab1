package hr.fer.apuw.lab1.services;

import hr.fer.apuw.lab1.models.Post;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post savePost(Post post, User user);
    Post findById(Long id);
    void deleteById(Long id, User user);
    Post createOrUpdatePost(Long id,Post post, User user);
}
