package team5.BW_CMR.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor(force = true)
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
    private String lastname;
    @Column
    private String avatarUrl;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ruoli_utente",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "Ruolo_id"))
    private Set<Ruolo> ruoli = new HashSet<>();

    public Utente(String username, String email, String password, String firstname, String lastname, Ruolo ruoli) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.avatarUrl = "https://ui-avatars.com/api/?name" + firstname + "+" + lastname;
        this.ruoli.add(ruoli);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNome()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
