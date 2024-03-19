package hv.hoviet.itpostbackend.service;

import hv.hoviet.itpostbackend.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAllPosts();

    Post findPostById(Integer id);
}
