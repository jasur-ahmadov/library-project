package az.egov.libraryproject.auth;

import az.egov.libraryproject.model.entity.Reader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Size(min = 8, message = "{input.password}")
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private boolean accountNonLocked = true;
    private boolean enabled = false;

    @OneToOne(mappedBy = "user")
    private Reader reader;

    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String password,
                   AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> permissions = appUserRole.getPermissions()
                .stream()
                .map(AppPermission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + appUserRole.name()));
        return permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}