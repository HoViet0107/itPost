package hv.hoviet.itpostbackend.controller.auth;

import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

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
}
