package pl.sda.arppl4.spring.security.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 28.07.2022
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ApplicationUser> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
