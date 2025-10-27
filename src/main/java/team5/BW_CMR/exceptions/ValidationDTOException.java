package team5.BW_CMR.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationDTOException extends RuntimeException {
    List<String> errorsList;

    public ValidationDTOException(String message, List<String> errorsList) {
        super(message);
        this.errorsList = errorsList;
    }
}
