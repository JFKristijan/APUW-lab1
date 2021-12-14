package hr.fer.apuw.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDTO extends UserDTOWithoutPosts{

    private List<PostDTO> posts;

    public UserDTO(Long id, String username) {
        super(id, username);
    }
    public UserDTO(Long id, String username,List<PostDTO> posts) {
        super(id, username);
        this.posts=posts;
    }

    public UserDTO() {
        super();
    }
}
