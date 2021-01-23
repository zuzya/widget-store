package me.zuzyan.core.storage.internal.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 24.01.2021 г.
 */
class WithFactorStorageTest {

    private WithFactorStorage storage = WithFactorStorage.create();

    /**
     * Test adding element with shifting:
     *
     * <li>given 1,2,3,6</li>
     * <li>add 2</li>
     * <li>expecting 1,2,3,4,6</li>
     */
    @Test
    void testAddWithShifting() {

        // Given
        final WithFactorWidgetEntity entity1 = buildWidgetEntity(new BigDecimal(1));
        storage.add(entity1);

        final WithFactorWidgetEntity entity19 = buildWidgetEntity(new BigDecimal("1.9"));
        storage.add(entity19);
        final WithFactorWidgetEntity entity191 = buildWidgetEntity(new BigDecimal("1.91"));
        storage.add(entity191);

        final WithFactorWidgetEntity entity2 = buildWidgetEntity(new BigDecimal(2));
        storage.add(entity2);

        // When
        final WithFactorWidgetEntity control = buildWidgetEntity(new BigDecimal("2"));
        storage.add(control);

        // Then
        assertEquals(5, storage.size());

        // 1
        final WithFactorWidgetEntity found1 = storage.find(entity1.getId());
        assertEquals(new BigDecimal(1), found1.getZIndex());

        final WithFactorWidgetEntity found19 = storage.find(entity19.getId());
        assertEquals(new BigDecimal("1.9"), found19.getZIndex());

        final WithFactorWidgetEntity found191 = storage.find(entity191.getId());
        assertEquals(new BigDecimal("1.91"), found191.getZIndex());

        // control
        final WithFactorWidgetEntity foundControl = storage.find(control.getId());
        assertEquals(new BigDecimal("1.911"), foundControl.getZIndex());

        // 2
        final WithFactorWidgetEntity found2 = storage.find(entity2.getId());
        assertEquals(new BigDecimal(2), found2.getZIndex());
    }

    protected WithFactorWidgetEntity buildWidgetEntity(BigDecimal zIndex) {

        final WithFactorWidgetEntity entity = new WithFactorWidgetEntity();
        entity.setId(1L);
        entity.setZIndex(zIndex);
        entity.setX(100);
        entity.setY(200);

        return entity;
    }
}
