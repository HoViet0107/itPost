package hv.hoviet.itpostbackend.controller;

import hv.hoviet.itpostbackend.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public ResponseEntity<?> getPosts(){
        try{
            return ResponseEntity.ok(postRepository.findAllPosts());
        }catch (Exception e){
            return ResponseEntity.ok(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // todo: đầu tiên trả về post đây đủ thông tin
    // todo: tạo lớp dto trả về comments của post
}
