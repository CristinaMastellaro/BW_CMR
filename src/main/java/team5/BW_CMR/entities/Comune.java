package team5.BW_CMR.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private int codiceProvincia;
    private int progressivoComune;
    private String denominazioneComune;
    private String provincia;

    public Comune(int codiceProvincia, int progressivoComune, String denominazioneComune, String provincia) {
        this.codiceProvincia = codiceProvincia;
        this.progressivoComune = progressivoComune;
        this.denominazioneComune = denominazioneComune;
        this.provincia = provincia;
    }
}
