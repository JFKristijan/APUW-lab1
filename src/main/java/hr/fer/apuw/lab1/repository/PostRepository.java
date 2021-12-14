package hr.fer.apuw.lab1.repository;

import hr.fer.apuw.lab1.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post,Long> {
}
