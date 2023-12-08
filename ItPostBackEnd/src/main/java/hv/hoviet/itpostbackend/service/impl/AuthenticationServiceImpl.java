package hv.hoviet.itpostbackend.service.impl;

import hv.hoviet.itpostbackend.dto.JwtAuthenticationResponse;
import hv.hoviet.itpostbackend.dto.SignInRequest;
import hv.hoviet.itpostbackend.dto.SignUpRequest;
import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import hv.hoviet.itpostbackend.respository.UserRepository;
import hv.hoviet.itpostbackend.service.AuthenticationService;
import hv.hoviet.itpostbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        Set roles = new HashSet<>();
        roles.add(EnumRole.ROLE_USER);

        user.setName(signUpRequest.getName());
        user.setNick_name(signUpRequest.getNick_name());
        user.setPhone(signUpRequest.getPhone());
        user.setEmail(signUpRequest.getEmail());
        user.setPass_word(passwordEncoder.encode(signUpRequest.getPass_word()));
        user.setAvatar_link(signUpRequest.getAvatar_link());
        user.setBanner_link(signUpRequest.getBanner_link());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(), signInRequest.getPass_word()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        User user = userRepository
                .findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));
        String jwt = jwtUtil.generateToken(user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        return jwtAuthenticationResponse;
    }
}
