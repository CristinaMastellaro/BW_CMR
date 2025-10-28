package team5.BW_CMR.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="Fatture_Stati")
@Data
@NoArgsConstructor
public class FatturaStato {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
   private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoFattura statoFattura;

    @Column(nullable = false)
    LocalDate dataStato;

    @ManyToOne
    private Fattura fattura;

    public FatturaStato( StatoFattura statoFattura, LocalDate dataStato, Fattura fattura) {
      this.statoFattura = statoFattura;
        this.dataStato = dataStato;
        this.fattura = fattura;
    }
}
