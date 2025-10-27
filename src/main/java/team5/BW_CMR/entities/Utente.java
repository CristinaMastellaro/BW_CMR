package team5.BW_CMR.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

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

    public void addRuolo(Ruolo ruolo) {
        this.ruoli.add(ruolo);
    }

}
