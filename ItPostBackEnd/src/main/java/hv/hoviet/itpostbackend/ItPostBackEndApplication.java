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

        Optional<User> admin = userRepository.findByEmail("admin@hv.com");
        if(!admin.isPresent()){
            User user = new User();

            Optional<Role> adminRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role adminRole = adminRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));

            user.setName("Ho Viet");
            user.setPhone("0123456789");
            user.setEmail("admin@hv.com");
            user.setPass_word("123");
            userRepository.save(user);
            user.getRoles().add(adminRole);
            userRepository.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ItPostBackEndApplication.class, args);
    }

}
