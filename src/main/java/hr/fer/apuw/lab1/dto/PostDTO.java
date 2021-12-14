package hr.fer.apuw.lab1.dto;

import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String text;
    private UserDTOWithoutPosts poster;

    public PostDTO() {
    }
}
