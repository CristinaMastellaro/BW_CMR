package team5.BW_CMR.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Fatture")
@Data
@NoArgsConstructor
public class Fattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate data;
    private double importo;
    private long numero;
    @Enumerated(EnumType.STRING)
    private StatoFattura stato;

    @ManyToOne
    private Cliente cliente;

    public Fattura(LocalDate data, double importo, StatoFattura stato, Cliente cliente) {
        this.data = data;
        this.importo = importo;
        this.stato = stato;
        this.cliente = cliente;
    }
}
