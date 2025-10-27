package team5.BW_CMR.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import team5.BW_CMR.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(
@NotNull(message="La data è obbligatoria")
@FutureOrPresent(message="La data deve essere ordierna o futura!")
LocalDate data,

@Min(value= 1, message="L'importo deve essere maggiore di zero!")
double importo,

@NotNull(message="Il numero di fattura è obbligatorio!")
Long numero,

@NotNull(message="Lo stato è obbligatorio")
StatoFattura stato,

@NotNull(message="Il cliente è obbligatorio!")
UUID clienteID

){}
