package hr.fer.apuw.lab1.services;

import hr.fer.apuw.lab1.models.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post savePost(Post post);
    Post findById(Long id);
    void deleteById(Long id);
    Post createOrUpdatePost(Long id,Post post);
}
