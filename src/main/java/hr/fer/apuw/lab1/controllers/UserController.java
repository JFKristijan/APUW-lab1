package hr.fer.apuw.lab1.controllers;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.dto.UserDTO;
import hr.fer.apuw.lab1.mappers.Mapper;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.services.PostService;
import hr.fer.apuw.lab1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;


    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(
                userService.findAll().stream().map(Mapper::UserToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return new ResponseEntity<>(
                Mapper.UserToDTO(userService.saveUser(user)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(
                Mapper.UserToDTO(userService.findById(id)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> createOrUpdateUser(@PathVariable Long id, @RequestBody User user){
        return new ResponseEntity<>(
                Mapper.UserToDTO(userService.createOrUpdateUser(id, user)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPostsFromUser(@PathVariable Long id) {
        return new ResponseEntity<>(
                userService.findById(id).getPosts().stream().map(Mapper::PostToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }


}
