package team5.BW_CMR.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor

public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String firstname;

    @Column
    private  String lastname;

    @Column
    private String avatarUrl;

    @ManyToMany
    @JoinTable(
            name = "ruoli_utente",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "Ruolo_id")
    )
    private Set<Ruolo> ruoli = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream().map(ruolo -> new SimpleGrantedAuthority("ROLE_" + ruolo.getNome())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
}
