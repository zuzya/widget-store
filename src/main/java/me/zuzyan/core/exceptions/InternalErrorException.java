package me.zuzyan.core.exceptions;

import lombok.Getter;

/**
 * Internal error exception
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
@Getter
public class InternalErrorException extends BaseCheckedException {

    public InternalErrorException(String message) {

        super(ExceptionConstants.INTERNAL_ERROR, message);
    }

}
