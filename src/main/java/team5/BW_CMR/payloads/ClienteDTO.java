package team5.BW_CMR.payloads;

import jakarta.validation.constraints.*;
import team5.BW_CMR.entities.RagioneSociale;

import java.time.LocalDate;

public record ClienteDTO(
        @NotNull
        @Pattern(regexp = "\\d{11}", message = "La partita IVA deve essere di 11 cifre")
        String partitaIva,

        @NotBlank
        @Email
        String email,

        @NotNull
        RagioneSociale ragioneSociale,

        @NotNull
        @PastOrPresent
        LocalDate dataInserimento,

        @NotNull
        @PastOrPresent
        LocalDate dataUltimoContatto,

        @PositiveOrZero
        double fatturatoAnnuale,

        @NotBlank
        @Email
        String pec,

        @NotNull
        long telefono,

        @NotBlank
        @Email
        String emailContatto,

        @NotBlank
        String nomeContatto,

        @NotBlank
        String cognomeContatto,

        @NotNull
        long telefonoContatto
) {}