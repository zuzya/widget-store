package me.zuzyan.core.storage.db.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.zuzyan.AbstractCommonTest;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.impl.Container;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
class LinkedStorageTest extends AbstractCommonTest {

    @Autowired
    private WidgetStorage<Container> linkedStorage;

    @BeforeEach
    void setUp() {

        linkedStorage.deleteAll();
    }

    @Test
    void testAdd() {

        // Given
        final WidgetEntity entity1 = buildWidgetEntity(1);
        linkedStorage.add(entity1);

        // When
        final WidgetEntity entity2 = buildWidgetEntity(entity1.getZIndex() + 1);
        linkedStorage.add(entity2);

        // Then
        assertEquals(2, linkedStorage.size());

        final Container found2 = linkedStorage.find(entity2.getId());
        assertEquals(found2.getWidget().getZIndex(), entity2.getZIndex());
        assertNull(found2.getNext());

        final Container found1 = linkedStorage.find(entity1.getId());
        assertEquals(found1.getNext(), found2);
        assertEquals(found1.getWidget().getZIndex(), entity1.getZIndex());
    }

    @Test
    void testAddWithShifting() {

        // Given
        final Integer z1 = 1;
        final WidgetEntity entity1 = buildWidgetEntity(z1);
        linkedStorage.add(entity1);

        final Integer z2 = z1;
        final WidgetEntity entity2 = buildWidgetEntity(z2);

        // When
        linkedStorage.add(entity2);

        // Then
        assertEquals(2, linkedStorage.size());

        final Container found1 = linkedStorage.find(entity1.getId());
        assertNull(found1.getNext());
        assertEquals(found1.getWidget().getZIndex(), z1 + 1);

        final Container found2 = linkedStorage.find(entity2.getId());
        assertEquals(found2.getNext(), found1);
        assertEquals(found2.getWidget().getZIndex(), z2);
    }

    @Test
    void testAddWithShiftingWideCase() {

        // Given
        final Integer z1 = 1;
        final WidgetEntity entity1 = buildWidgetEntity(z1);
        linkedStorage.add(entity1);

        final Integer z2 = 2;
        final WidgetEntity entity2 = buildWidgetEntity(z2);
        linkedStorage.add(entity2);

        final Integer z3 = 3;
        final WidgetEntity entity3 = buildWidgetEntity(z3);
        linkedStorage.add(entity3);

        final Integer z4 = 6;
        final WidgetEntity entity4 = buildWidgetEntity(z4);
        linkedStorage.add(entity4);

        // When
        final WidgetEntity control = buildWidgetEntity(2);
        linkedStorage.add(control);

        // Then
        assertEquals(5, linkedStorage.size());

        // 1
        final Container found1 = linkedStorage.find(entity1.getId());
        assertEquals(found1.getWidget().getZIndex(), z1);

        // control
        final Container foundControl = linkedStorage.find(control.getId());
        assertEquals(foundControl.getWidget().getZIndex(), 2);

        // 2
        final Container found2 = linkedStorage.find(entity2.getId());
        assertEquals(found2.getWidget().getZIndex(), z2 + 1);

        // 3
        final Container found3 = linkedStorage.find(entity3.getId());
        assertEquals(found3.getWidget().getZIndex(), z3 + 1);

        // 4
        final Container found4 = linkedStorage.find(entity4.getId());
        assertEquals(found4.getWidget().getZIndex(), z4);

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
