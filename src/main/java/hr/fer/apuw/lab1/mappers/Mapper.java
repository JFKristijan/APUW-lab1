package hr.fer.apuw.lab1.mappers;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.dto.UserDTO;
import hr.fer.apuw.lab1.dto.UserDTOWithoutPosts;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public  class Mapper {

    public static UserDTO UserToDTO(User user){
        return new UserDTO(user.getId(),
                user.getUsername(),
                Objects.requireNonNullElseGet(user.getPosts(),
                        (Supplier<List<Post>>) ArrayList::new).stream().map(Mapper::PostToDTO).collect(Collectors.toList()));
    }

    public static UserDTOWithoutPosts UserDTOWithoutPosts(User user){
        return new UserDTOWithoutPosts(user.getId(),
                user.getUsername()
        );
    }

    public static PostDTO PostToDTO(Post post){
        return new PostDTO(
                post.getId(),
                post.getText(),
                Mapper.UserDTOWithoutPosts(post.getPoster())
        );
    }
}
