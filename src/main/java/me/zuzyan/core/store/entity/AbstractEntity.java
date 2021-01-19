package me.zuzyan.core.store.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
public class AbstractEntity implements Serializable {

    protected String id;

    protected int version;

    protected LocalDateTime creationTime;

    protected LocalDateTime modificationTime;
}
