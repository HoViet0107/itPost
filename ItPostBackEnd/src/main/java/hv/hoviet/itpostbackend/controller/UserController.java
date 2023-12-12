package hv.hoviet.itpostbackend.controller;

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

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String token) {
        try {
            String extractedToken = token.substring(7);
            Set userRoles = authorityUtil.extractRoles(extractedToken);

            if (userRoles.contains("ROLE_ADMIN")) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Hi admin!"));
            } else {
                return ResponseEntity.ok(Collections.singletonMap("message", "Hi user!"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
