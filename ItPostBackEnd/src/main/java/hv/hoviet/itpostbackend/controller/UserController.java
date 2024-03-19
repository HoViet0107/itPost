package hv.hoviet.itpostbackend.controller;

import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.util.AuthorityUtil;
import hv.hoviet.itpostbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthorityUtil authorityUtil;


}
