package me.zuzyan.core.storage.internal.impl;

import java.math.BigDecimal;

import javax.persistence.Transient;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Entity for experiment with 'withfactor' algorithm
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
public class WithFactorWidgetEntity extends WidgetEntity {

    private BigDecimal zIndexEx;

    @Transient
    @Override
    public int compareTo(WidgetEntity o) {

        if (o instanceof WithFactorWidgetEntity) {

            WithFactorWidgetEntity w = (WithFactorWidgetEntity) o;

            if (this.getZIndexEx().compareTo(w.getZIndexEx()) != 0) {
                return this.getZIndexEx().compareTo(w.getZIndexEx());
            } else if (this.equals(w)) {
                return 0;
            }
            return -1;
        }
        return -1;
    }
}
