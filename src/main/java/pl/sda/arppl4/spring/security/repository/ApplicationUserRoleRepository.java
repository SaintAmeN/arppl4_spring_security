package pl.sda.arppl4.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.arppl4.spring.security.model.ApplicationUserRole;

import java.util.Optional;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
public interface ApplicationUserRoleRepository
        extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findByName(String name);
    boolean existsByName(String name);
}
