package me.zuzyan.core.storage.db.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.zuzyan.AbstractCommonTest;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
class SkipListSetStorageTest extends AbstractCommonTest {

    @Autowired
    private WidgetStorage<WidgetEntity> skipListSetStorage;

    @BeforeEach
    void setUp() {

        skipListSetStorage.deleteAll();
    }

    @Test
    void testAdd() {

        // Given
        final WidgetEntity entity1 = buildWidgetEntity(1);
        skipListSetStorage.add(entity1);

        // When
        final WidgetEntity entity2 = buildWidgetEntity(entity1.getZIndex() + 1);
        skipListSetStorage.add(entity2);

        // Then
        assertEquals(2, skipListSetStorage.size());
    }

    @Test
    void testAddWithShifting() {

        // Given
        final Integer z1 = 1;
        final WidgetEntity entity1 = buildWidgetEntity(z1);
        skipListSetStorage.add(entity1);

        final Integer z2 = z1;
        final WidgetEntity entity2 = buildWidgetEntity(z2);

        // When
        skipListSetStorage.add(entity2);

        // Then
        assertEquals(2, skipListSetStorage.size());

        final WidgetEntity found1 = skipListSetStorage.find(entity1.getId());
        assertEquals(found1.getZIndex(), z1 + 1);

        final WidgetEntity found2 = skipListSetStorage.find(entity2.getId());
        assertEquals(found2.getZIndex(), z2);
    }

    @Test
    void testAddWithShiftingWideCase() {

        // Given
        final Integer z1 = 1;
        final WidgetEntity entity1 = buildWidgetEntity(z1);
        skipListSetStorage.add(entity1);

        final Integer z2 = 2;
        final WidgetEntity entity2 = buildWidgetEntity(z2);
        skipListSetStorage.add(entity2);

        final Integer z3 = 3;
        final WidgetEntity entity3 = buildWidgetEntity(z3);
        skipListSetStorage.add(entity3);

        final Integer z4 = 6;
        final WidgetEntity entity4 = buildWidgetEntity(z4);
        skipListSetStorage.add(entity4);

        // When
        final WidgetEntity control = buildWidgetEntity(2);
        skipListSetStorage.add(control);

        // Then
        assertEquals(5, skipListSetStorage.size());

        // 1
        final WidgetEntity found1 = skipListSetStorage.find(entity1.getId());
        assertEquals(found1.getZIndex(), z1);

        // control
        final WidgetEntity foundControl = skipListSetStorage.find(control.getId());
        assertEquals(foundControl.getZIndex(), 2);

        // 2
        final WidgetEntity found2 = skipListSetStorage.find(entity2.getId());
        assertEquals(found2.getZIndex(), z2 + 1);

        // 3
        final WidgetEntity found3 = skipListSetStorage.find(entity3.getId());
        assertEquals(found3.getZIndex(), z3 + 1);

        // 4
        final WidgetEntity found4 = skipListSetStorage.find(entity4.getId());
        assertEquals(found4.getZIndex(), z4);

    }

    private WidgetEntity buildWidgetEntity(int zIndex) {

        final WidgetEntity entity = new WidgetEntity();
        entity.setId(1L);
        entity.setZIndex(zIndex);
        entity.setX(100);
        entity.setY(200);

        return entity;
    }
}
