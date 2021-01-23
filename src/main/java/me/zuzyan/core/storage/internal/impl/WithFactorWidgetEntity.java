package me.zuzyan.core.storage.internal.impl;

import java.math.BigDecimal;

import javax.persistence.Transient;

import lombok.Data;
import me.zuzyan.core.storage.entity.AbstractEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
public class WithFactorWidgetEntity extends AbstractEntity
        implements Comparable<WithFactorWidgetEntity> {

    private Integer x;

    private Integer y;

    private BigDecimal zIndex;

    private Integer width;

    private Integer height;

    public WithFactorWidgetEntity() {

    }

    @Transient
    @Override
    public int compareTo(WithFactorWidgetEntity o) {

        if (this.getZIndex().compareTo(o.getZIndex()) != 0) {
            return this.getZIndex().compareTo(o.getZIndex());
        } else if (this.equals(o)) {
            return 0;
        }
        return -1;
    }
}
