package hv.hoviet.itpostbackend.controller;

import com.auth0.jwt.JWT;
import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import hv.hoviet.itpostbackend.util.AuthorityUtil;
import hv.hoviet.itpostbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping()
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok("Hi User!");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
