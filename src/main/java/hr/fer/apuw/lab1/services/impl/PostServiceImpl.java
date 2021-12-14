package hr.fer.apuw.lab1.services.impl;

import hr.fer.apuw.lab1.exception.ForbiddenAccessException;
import hr.fer.apuw.lab1.exception.NoSuchEntityException;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.repository.PostRepository;
import hr.fer.apuw.lab1.repository.UserRepository;
import hr.fer.apuw.lab1.services.PostService;
import hr.fer.apuw.lab1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    private final UserService userService;


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Post post, org.springframework.security.core.userdetails.User user) {
        User u = getUser(user);
        post.setPoster(u);

        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public void deleteById(Long id, org.springframework.security.core.userdetails.User user) {
        postRepository.findById(id).ifPresent(post -> {
            ensureUserAndPosterMatch(post,getUser(user));
            postRepository.delete(post);
        });
    }

    @Override
    public Post createOrUpdatePost(Long id, Post post, org.springframework.security.core.userdetails.User user) {
        Optional<Post> p = postRepository.findById(id);
        User u = getUser(user);
        Post toSave;
        if(p.isPresent()){
            ensureUserAndPosterMatch(post, u);
            toSave = p.get();

            if(post.getText()!=null){
                toSave.setText(post.getText());
            }
        }else {
            post.setPoster(u);
            post.setId(id);
            toSave = post;
        }
        return postRepository.save(toSave);
    }

    private void ensureUserAndPosterMatch(Post post, User user){
        if( post.getPoster() != null &&
            !userService.findByUsername(
                user.getUsername()).getId()
                .equals(
                post.getPoster().getId())){
            throw new ForbiddenAccessException();
        }
    }

    private User getUser(org.springframework.security.core.userdetails.User user){
        return userService.findByUsername(user.getUsername());
    }

}
