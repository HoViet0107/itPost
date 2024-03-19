package hv.hoviet.itpostbackend.service.impl;

import hv.hoviet.itpostbackend.model.Post;
import hv.hoviet.itpostbackend.respository.PostRepository;
import hv.hoviet.itpostbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAllPosts();
    }
    @Override
    public Post findPostById(Integer id) {
        return postRepository.findPostById(id);
    }
}
