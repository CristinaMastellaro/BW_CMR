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
@AllArgsConstructor
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

//    @ManyToMany
//    @JoinTable(
//            name = "ruoli_utente",
//            joinColumns = @JoinColumn(name = "utente_id"),
//            inverseJoinColumns = @JoinColumn(name = "Ruolo_id")
//    )
//    private Set<Ruolo> ruoli = new HashSet<>();

    private List<Ruolo> ruoli ;

//    public void addRuolo(Ruolo ruolo) {
//        this.ruoli.add(ruolo);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleRuolo = new ArrayList<>();
        this.ruoli.forEach(Ruolo -> {
            simpleRuolo.add(new SimpleGrantedAuthority(Ruolo.name()));
        });
        return simpleRuolo;
    }
}
