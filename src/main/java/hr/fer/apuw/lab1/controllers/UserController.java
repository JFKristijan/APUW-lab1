package hr.fer.apuw.lab1.controllers;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.dto.UserDTO;
import hr.fer.apuw.lab1.mappers.Mapper;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.services.PostService;
import hr.fer.apuw.lab1.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @Operation(summary = "Get a list of all users")
    @ApiResponse(responseCode = "200", description = "All the users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(
                userService.findAll().stream().map(Mapper::UserToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new user"),
            @ApiResponse(responseCode = "400", description = "Username already exists",
                    content = @Content())
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return new ResponseEntity<>(
                Mapper.UserToDTO(userService.saveUser(user)),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User exists and is returned"),
            @ApiResponse(responseCode = "404", description = "User with given id does not exist",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@Parameter(description = "id of the user to be found") @PathVariable Long id) {
        return new ResponseEntity<>(
                Mapper.UserToDTO(userService.findById(id)),
                HttpStatus.OK);
    }
//requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                   content = @Content())
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted if exists"),
        @ApiResponse(responseCode = "401", description = "User is not logged in (basic auth) or wrong credentials",
                content = @Content()),
            @ApiResponse(responseCode = "403", description = "User is trying to delete another user",
                    content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "id of the user to be deleted")
                                               @PathVariable Long id,
                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user){
        userService.deleteById(id, user);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }
    //TODO add swagger
    @PutMapping("/{id}")
    public ResponseEntity<Void> createOrUpdateUser(@PathVariable Long id, @RequestBody User user, @AuthenticationPrincipal org.springframework.security.core.userdetails.User secUser){
        userService.updateUser(id, user, secUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all posts from user with given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All posts from user with given id"),
        @ApiResponse(responseCode = "404", description = "User with given id not found",
                content = @Content())
    })
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPostsFromUser(@Parameter(description = "id of the user from which to get posts")
                                                              @PathVariable Long id) {
        return new ResponseEntity<>(
                userService.findById(id).getPosts().stream().map(Mapper::PostToDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }


}
