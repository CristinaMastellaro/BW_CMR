package team5.BW_CMR.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorDTO(
        String message,
        List<String> errorsList,
        LocalDateTime timeStamp
) {
}
