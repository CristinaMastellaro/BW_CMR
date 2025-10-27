package team5.BW_CMR.payloads;

import jakarta.validation.constraints.NotBlank;

public record IndirizzoDTO(
        @NotBlank(message = "")
        String via,
        int civico,
        int cap,
        int localita,
        String comune
) {
}
