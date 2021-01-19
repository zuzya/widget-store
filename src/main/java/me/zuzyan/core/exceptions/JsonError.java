package me.zuzyan.core.exceptions;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
public class JsonError {

    public final int code;

    public final String message;

    public final Object data;

    public JsonError(int code, String message, Object data) {

        this.code = code;
        this.message = message;
        this.data = data;
    }
}
