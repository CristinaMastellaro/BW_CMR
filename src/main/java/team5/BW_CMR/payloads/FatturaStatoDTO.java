package team5.BW_CMR.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import team5.BW_CMR.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;

public record FatturaStatoDTO (

        @NotNull(message="Lo stato della fattura è obbligatorio")
        StatoFattura statoFattura,

        @NotNull(message="La data dello stato della fattura è obbligatoria")
        @PastOrPresent(message="La data dello stato della fattura non può essere futura")
        LocalDate dataStato,

        @NotNull(message="La fattura è obbligatoria")
        UUID fatturaId

        ){}
