package hr.fer.apuw.lab1.services.impl;

import hr.fer.apuw.lab1.exception.NoSuchEntityException;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.repository.PostRepository;
import hr.fer.apuw.lab1.repository.UserRepository;
import hr.fer.apuw.lab1.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Post post) {
        //TODO New exception for this
        User u = userRepository.findById(post.getPoster().getId()).orElseThrow(NoSuchEntityException::new);
        Post p = postRepository.save(post);
        p.setPoster(u);
        return p;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.findById(id).ifPresent(postRepository::delete);
    }

    @Override
    public Post createOrUpdatePost(Long id, Post post) {
        Optional<Post> p = postRepository.findById(id);
        Post toSave;
        if(p.isPresent()){
            toSave = p.get();
            if(post.getText()!=null){
                toSave.setText(post.getText());
            }
        }else {
            toSave = post;
        }
        return postRepository.save(toSave);
    }
}
