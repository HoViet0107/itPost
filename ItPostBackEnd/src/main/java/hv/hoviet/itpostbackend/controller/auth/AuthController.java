package hv.hoviet.itpostbackend.controller.auth;

import hv.hoviet.itpostbackend.dto.SignInRequest;
import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.service.AuthenticationService;
import hv.hoviet.itpostbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        // 7:26
        try {
            authenticationService.signUp(signUpRequest);
            return new ResponseEntity<>("Đăng ký thành công!", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        try {
            return new ResponseEntity<>(authenticationService.signIn(signInRequest), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> sign_in(@RequestBody SignInRequest signInRequest) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    signInRequest.getEmail(), signInRequest.getPass_word()));

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(
                                    user))
                    .body(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
