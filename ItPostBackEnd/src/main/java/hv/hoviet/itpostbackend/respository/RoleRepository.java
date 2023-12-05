package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Role;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole_name(EnumRole role_name);
}
