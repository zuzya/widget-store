package me.zuzyan.core.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception descriptions
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
@RequiredArgsConstructor
@Getter
public enum ExceptionConstants {

    VALIDATION_ERROR(
            6000,
            "Validation error"),

    EXTERNAL_INTEGRATION_ERROR(
            8000,
            "External integration error"),

    INTERNAL_ERROR(
            8001,
            "Internal error");

    private final int code;

    private final String message;
}
