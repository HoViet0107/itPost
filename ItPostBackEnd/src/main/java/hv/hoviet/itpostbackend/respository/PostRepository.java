package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
