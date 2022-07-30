package pl.sda.arppl4.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.arppl4.spring.security.model.ApplicationUser;

import java.util.Optional;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
public interface ApplicationUserRepository
        extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
