package com.osvaldoga.errorhandler.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class APIValidationError extends APISubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
