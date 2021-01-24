package me.zuzyan.core.storage.internal.impl;

import static me.zuzyan.core.TestTags.FAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Test for {@link me.zuzyan.core.storage.internal.impl.SkipListSetStorage}
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Tag(FAST)
abstract class AbstractStorageTest {

    private WidgetStorage storage = getStorage();

    protected abstract WidgetStorage getStorage();

    @BeforeEach
    void setUp() {

        storage.deleteAll();
    }

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
        final WidgetEntity entity1 = buildWidgetEntity(1);
        storage.add(entity1);

        final WidgetEntity entity2 = buildWidgetEntity(2);
        storage.add(entity2);

        final WidgetEntity entity3 = buildWidgetEntity(3);
        storage.add(entity3);

        final WidgetEntity entity4 = buildWidgetEntity(6);
        storage.add(entity4);

        // When
        final WidgetEntity control = buildWidgetEntity(2);
        storage.add(control);

        // Then
        assertEquals(5, storage.size());

        // 1
        final WidgetEntity found1 = storage.find(entity1.getId());
        assertEquals(1, found1.getZIndex());

        // control
        final WidgetEntity foundControl = storage.find(control.getId());
        assertEquals(2, foundControl.getZIndex());

        // 2
        final WidgetEntity found2 = storage.find(entity2.getId());
        assertEquals(3, found2.getZIndex());

        // 3
        final WidgetEntity found3 = storage.find(entity3.getId());
        assertEquals(4, found3.getZIndex());

        // 4
        final WidgetEntity found4 = storage.find(entity4.getId());
        assertEquals(6, found4.getZIndex());
    }

    protected WidgetEntity buildWidgetEntity(int zIndex) {

        final WidgetEntity entity = new WidgetEntity();
        entity.setId(1L);
        entity.setZIndex(zIndex);
        entity.setX(100);
        entity.setY(200);

        return entity;
    }
}
