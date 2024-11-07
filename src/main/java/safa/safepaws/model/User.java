package safa.safepaws.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import safa.safepaws.enums.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user" , schema = "public", catalog = "safe_paws")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username" , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rol",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role rol;

    @Column(name = "ban", nullable = false)
    private boolean ban;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client_id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.ban;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.ban;
    }

}
