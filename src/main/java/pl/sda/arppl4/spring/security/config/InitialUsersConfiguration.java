package pl.sda.arppl4.spring.security.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 28.07.2022
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "default")
public class InitialUsersConfiguration {
    private List<String> roles;
    private List<InitialUserInfo> users;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitialUserInfo {
        private String username;
        private String password;
        private List<String> roles;
    }
}
