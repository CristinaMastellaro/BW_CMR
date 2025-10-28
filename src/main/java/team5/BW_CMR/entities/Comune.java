package team5.BW_CMR.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Comuni")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private long progressivoComune;
    private int codiceProvincia;
    private String denominazioneComune;

    @ManyToOne
    private Provincia provincia;

    public Comune(int codiceProvincia, int progressivoComune, String denominazioneComune, Provincia provincia) {
        this.codiceProvincia = codiceProvincia;
        this.progressivoComune = progressivoComune;
        this.denominazioneComune = denominazioneComune;
        this.provincia = provincia;
    }
}
