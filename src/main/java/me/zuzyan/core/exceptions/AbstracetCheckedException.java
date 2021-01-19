package me.zuzyan.core.exceptions;

import lombok.Getter;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
@Getter
public class AbstracetCheckedException extends Exception {

    private Integer code;

    private String shortMessage;

    private Object data;

    protected AbstracetCheckedException(Integer code, String shortMessage, Object data,
                                     String throwMessage) {

        super(throwMessage);
        this.code = code;
        this.shortMessage = shortMessage;
        this.data = data;
    }

    protected AbstracetCheckedException(Integer code, String shortMessage) {

        super();
        this.code = code;
        this.shortMessage = shortMessage;
    }

    protected AbstracetCheckedException(ExceptionConstants exConstant, Object data, String throwMessage) {

        this(exConstant.getCode(), exConstant.getMessage(), data, throwMessage);
    }

    protected AbstracetCheckedException(ExceptionConstants exConstant, String data) {

        super();
        this.code = exConstant.getCode();
        this.shortMessage = exConstant.getMessage();
        this.data = data;
    }

    protected AbstracetCheckedException(ExceptionConstants exConstant, Object data) {

        this(exConstant, data, exConstant.getMessage());
    }

}
