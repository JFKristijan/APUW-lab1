package hr.fer.apuw.lab1.dto;

import hr.fer.apuw.lab1.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserDTOWithoutPosts {
    private Long id;
    private String username;

    public UserDTOWithoutPosts() {
    }
}
