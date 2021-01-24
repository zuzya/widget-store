package me.zuzyan.core.exceptions;

import lombok.Getter;

/**
 * Base checked exception
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
@Getter
public class BaseCheckedException extends Exception {

    private Integer code;

    private String shortMessage;

    private Object data;

    protected BaseCheckedException(Integer code, String shortMessage, Object data,
                                   String throwMessage) {

        super(throwMessage);
        this.code = code;
        this.shortMessage = shortMessage;
        this.data = data;
    }

    protected BaseCheckedException(Integer code, String shortMessage) {

        super();
        this.code = code;
        this.shortMessage = shortMessage;
    }

    protected BaseCheckedException(ExceptionConstants exConstant, Object data, String throwMessage) {

        this(exConstant.getCode(), exConstant.getMessage(), data, throwMessage);
    }

    protected BaseCheckedException(ExceptionConstants exConstant, String data) {

        super();
        this.code = exConstant.getCode();
        this.shortMessage = exConstant.getMessage();
        this.data = data;
    }

    protected BaseCheckedException(ExceptionConstants exConstant, Object data) {

        this(exConstant, data, exConstant.getMessage());
    }

}
