package hr.fer.apuw.lab1.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User poster;

    @OneToMany(mappedBy = "parent")
    private List<Post> replies;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Post parent;


}
