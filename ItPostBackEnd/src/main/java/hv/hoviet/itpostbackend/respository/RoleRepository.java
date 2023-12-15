package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Role;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.role_name = :role_name")
    Optional<Role> findByRole_name(EnumRole role_name);
}
