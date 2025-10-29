package team5.BW_CMR.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
//@Data
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "ruoli")
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    //    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @ManyToMany(mappedBy = "ruoli")
    private Set<Utente> utenti = new HashSet<>();

    public Ruolo(String nome) {
        this.nome = nome;
    }
}

//public enum Ruolo {
//    ADMIN, USER
//
//}
