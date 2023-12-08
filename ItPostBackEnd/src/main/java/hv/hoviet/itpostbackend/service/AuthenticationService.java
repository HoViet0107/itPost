package hv.hoviet.itpostbackend.service;

import hv.hoviet.itpostbackend.dto.JwtAuthenticationResponse;
import hv.hoviet.itpostbackend.dto.SignInRequest;
import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.model.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
}
