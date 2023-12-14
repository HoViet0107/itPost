package hv.hoviet.itpostbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PostController {

    public ResponseEntity<?> getPosts(){
        try{
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
