package team5.BW_CMR.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IndirizzoDTO(
        @NotBlank(message = "'via' must not be blank")
        String via,
        @NotNull(message = "'civico' must not be null")
        @Min(value = 1, message = "'civico' must me higher than 0")
        int civico,
        @NotNull(message = "'cap' must not be null")
        @Min(value = 10000, message = "Cap is not written correctly")
        @Max(value = 99999, message = "Cap is not written correctly")
        int cap,
        String localita,
        @NotBlank(message = "'comune' must not be blank")
        String comune
) {
}
