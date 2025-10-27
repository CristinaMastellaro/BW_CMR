package team5.BW_CMR.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errors;

    public ValidationException(List<String> errors) {
        super("Errori nella validazoione del payload");
        this.errors = errors;
    }
}
