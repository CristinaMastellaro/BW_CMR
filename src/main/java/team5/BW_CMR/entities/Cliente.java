package team5.BW_CMR.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Clienti")
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private Long patitaIva;
    private String email;
    @Enumerated(EnumType.STRING)
    private RagioneSociale ragioneSociale;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    private String pec;
    private long telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private long telefonoContatto;
    private String logoAziendale;

    @ManyToOne
    @JoinColumn(name = "indirizzo_legale_id", nullable = false)
    private Indirizzo indirizzoLegale;

    @ManyToOne
    @JoinColumn(name = "indirizzo_operativo_id", nullable = false)
    private Indirizzo indirizzoOperativo;


    public Cliente(Long patitaIva, String email, RagioneSociale ragioneSociale, LocalDate dataInserimento, LocalDate dataUltimoContatto, double fatturatoAnnuale, String pec, long telefono, String emailContatto, String nomeContatto, String cognomeContatto, long telefonoContatto, Indirizzo indirizzoLegale, Indirizzo indirizzoOperativo) {
        this.patitaIva = patitaIva;
        this.email = email;
        this.ragioneSociale = ragioneSociale;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.indirizzoLegale = indirizzoLegale;
        this.indirizzoOperativo = indirizzoOperativo;

    }
}
