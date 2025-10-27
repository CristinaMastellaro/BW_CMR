package team5.BW_CMR.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record IndirizzoDTO(
        @NotBlank(message = "'via' must not be blank")
        String via,
        @NotBlank(message = "'civico' must not be blank")
        @Min(value = 1, message = "'civico' must me higher than 0")
        int civico,
        @NotBlank(message = "'cap' must not be blank")
        @Min(value = 10000, message = "Cap is not written correctly")
        @Max(value = 99999, message = "Cap is not written correctly")
        int cap,
        int localita,
        @NotBlank(message = "'comune' must not be blank")
        String comune
) {
}
