package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Boolean existsByEmail (String email);

    Boolean existsByPhone (String phone);
}
