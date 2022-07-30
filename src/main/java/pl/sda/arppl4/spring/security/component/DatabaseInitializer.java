package pl.sda.arppl4.spring.security.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.sda.arppl4.spring.security.config.InitialUsersConfiguration;
import pl.sda.arppl4.spring.security.model.ApplicationUser;
import pl.sda.arppl4.spring.security.model.ApplicationUserRole;
import pl.sda.arppl4.spring.security.repository.ApplicationUserRepository;
import pl.sda.arppl4.spring.security.repository.ApplicationUserRoleRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserRoleRepository applicationUserRoleRepository;

    // inicjalna konfiguracja bazy danych (związana z użytkownikami)
    private final InitialUsersConfiguration initialUsersConfiguration;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeDatabase() {
        createRoles();
        createUsers();
    }

    private void createUsers() {
        for (InitialUsersConfiguration.InitialUserInfo userInfo : initialUsersConfiguration.getUsers()) {
            createUserIfNotExists(userInfo);
        }
    }

    private void createUserIfNotExists(InitialUsersConfiguration.InitialUserInfo userInfo) {
        if (!applicationUserRepository.existsByUsername(userInfo.getUsername())) {

            Set<ApplicationUserRole> applicationUserRoleSet = userInfo.getRoles().stream()
                    .map(applicationUserRoleRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            ApplicationUser applicationUser = ApplicationUser.builder()
                    .username(userInfo.getUsername())
                    .password(userInfo.getPassword())
                    .roles(applicationUserRoleSet)
                    .build();

            applicationUserRepository.save(applicationUser);
        }
    }

    private void createRoles() {
        for (String roleName : initialUsersConfiguration.getRoles()) {
            createRoleIfNotExists(roleName);
        }
    }

    private void createRoleIfNotExists(String roleName) {
        if (!applicationUserRoleRepository.existsByName(roleName)) {
            ApplicationUserRole role = new ApplicationUserRole();
            role.setName(roleName);

            applicationUserRoleRepository.save(role);
        }
    }
}
