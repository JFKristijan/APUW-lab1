package hr.fer.apuw.lab1.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "poster")
    private List<Post> posts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
