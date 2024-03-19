package hv.hoviet.itpostbackend.service;


import hv.hoviet.itpostbackend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

}
