package hv.hoviet.itpostbackend;

import hv.hoviet.itpostbackend.model.Role;
import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import hv.hoviet.itpostbackend.respository.RoleRepository;
import hv.hoviet.itpostbackend.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class ItPostBackEndApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void run(String... args) throws Exception {
        // add role to database
        Optional<Role> roleAdmin = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
        if (!roleAdmin.isPresent()){
            Role role = new Role();
            role.setRole_name(EnumRole.ROLE_ADMIN);
            roleRepository.save(role);
        }
        Optional<Role> roleUser = roleRepository.findByRole_name(EnumRole.ROLE_USER);
        if (!roleUser.isPresent()){
            Role role = new Role();
            role.setRole_name(EnumRole.ROLE_USER);
            roleRepository.save(role);
        }

        // add admin and user to database
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<User> admin = userRepository.findByEmail("admin@hv.com");
        if(!admin.isPresent()){
            User user = new User();

            Optional<Role> adminRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role adminRole = adminRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));

            user.setName("Ho Viet");
            user.setPhone("0987654321");
            user.setEmail("admin@hv.com");
            user.setSubscribedOn(currentTime);
            user.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(user);
            user.getRoles().add(adminRole);
            userRepository.save(user);
        }

        Optional<User> optUser = userRepository.findByEmail("user@hv.com");
        if(!optUser.isPresent()){
            User user = new User();

            Optional<Role> userRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role userRole = userRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));

            user.setName("Hồ Quốc Việt");
            user.setPhone("0123456789");
            user.setEmail("user@hv.com");
            user.setSubscribedOn(currentTime);
            user.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(user);
            user.getRoles().add(userRole);
            userRepository.save(user);
        }

        // add post to database
        // add comment to database
        // add img to database
        // add tag to database
    }

    public static void main(String[] args) {
        SpringApplication.run(ItPostBackEndApplication.class, args);
    }

}
