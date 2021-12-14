package hr.fer.apuw.lab1.controllers;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.mappers.Mapper;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "Get a list of all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of posts returned"),
            @ApiResponse(responseCode = "401", description = "User is not logged in (basic auth) or wrong credentials",
                    content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(){
        return new ResponseEntity<>(
                postService.findAll().stream().map(Mapper::PostToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Operation(summary = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post successfully created"),
            @ApiResponse(responseCode = "401", description = "User is not logged in (basic auth) or wrong credentials",
                    content = @Content())
    })
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody Post post, @AuthenticationPrincipal User user){
        return new ResponseEntity<>(
                Mapper.PostToDTO(postService.savePost(post, user)),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found by id"),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content()),
            @ApiResponse(responseCode = "401", description = "User is not logged in (basic auth) or wrong credentials",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@Parameter(description = "id of the post to be found")
                                               @PathVariable Long id) {
        return new ResponseEntity<>(
                Mapper.PostToDTO(postService.findById(id)),
                HttpStatus.OK);
    }

    @Operation(summary = "Delete post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted if exists",
                    content = @Content()),
            @ApiResponse(responseCode = "401", description = "User is not logged in (basic auth) or wrong credentials",
                    content = @Content()),
            @ApiResponse(responseCode = "403", description = "User tried to delete post that is not his",
                    content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Parameter(description = "id of the post to be deleted")
                                               @PathVariable Long id,
                                           @AuthenticationPrincipal User user){
        postService.deleteById(id, user);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }

    //TODO implement just update??
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> createOrUpdatePost(@PathVariable Long id, @RequestBody Post post, @AuthenticationPrincipal User user){
        postService.createOrUpdatePost(id, post, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
