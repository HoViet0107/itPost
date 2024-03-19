package hv.hoviet.itpostbackend.controller.auth;

import hv.hoviet.itpostbackend.dto.SignInRequest;
import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        // 7:26
        try {
            try {
                authenticationService.signUp(signUpRequest);
                return ResponseEntity.ok(Collections.singletonMap("message", "Đăng ký thành công!"));
            } catch (IllegalStateException illEx) {
                System.out.println(illEx.getMessage());
                return ResponseEntity.ok(Collections.singletonMap("message", illEx.getMessage()));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.ok(Collections.singletonMap("message", ex.getMessage()));
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            System.out.println(signInRequest);
            if (!signInRequest.getEmail().equals(null) &&
                    !signInRequest.getPass_word().equals(null)) {
                return new ResponseEntity<>(authenticationService.signIn(signInRequest), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.ok(Collections.singletonMap("message", ex.getMessage()));
        }
    }
}
