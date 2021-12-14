package hr.fer.apuw.lab1.controllers;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.mappers.Mapper;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(){
        return new ResponseEntity<>(
                postService.findAll().stream().map(Mapper::PostToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody Post post){
        return new ResponseEntity<>(
                Mapper.PostToDTO(postService.savePost(post)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(
                Mapper.PostToDTO(postService.findById(id)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> createOrUpdatePost(@PathVariable Long id, @RequestBody Post post){
        return new ResponseEntity<>(
                Mapper.PostToDTO(postService.createOrUpdatePost(id, post)),
                HttpStatus.CREATED);
    }
}
