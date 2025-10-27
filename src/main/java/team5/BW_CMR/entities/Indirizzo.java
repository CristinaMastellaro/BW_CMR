package team5.BW_CMR.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "Indirizzi")
@Data
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String via;
    private int civico;
    private String localita;
    private int cap;

    @ManyToOne
    private Comune comune;

    public Indirizzo(String via, int civico, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
    }

    public Indirizzo(String via, int civico, String localita, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
