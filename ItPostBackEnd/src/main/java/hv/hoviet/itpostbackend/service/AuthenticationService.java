package hv.hoviet.itpostbackend.service;

import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.model.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
}
