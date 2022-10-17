package pl.jklata.budgetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString

public class ErrorDto {

    private final int httpStatus;
    private final String message;

}
