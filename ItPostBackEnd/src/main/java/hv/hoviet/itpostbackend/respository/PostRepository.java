package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p")
    List<Post> findAllPosts();

    Post findPostById(Integer id);
}
