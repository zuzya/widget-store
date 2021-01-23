package me.zuzyan.core.storage.internal.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Performance test for {@link SkipListSetStorage}
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Slf4j
class WidgetStoragePerformanceTest {

    @Test
    void testAdd() {

        final ConcurrentSkipListSet<WidgetEntity> collection = new ConcurrentSkipListSet<>();
        fill(collection, 9000000);
    }

    @Test
    void testAddWithStorage() {

        WidgetStorage collection = SkipListSetStorage.create();
        int elementsNumber = 9000;
        computeStorage(collection, elementsNumber);
    }

    @Test
    void testAddWithLinkedStorage() {

        WidgetStorage collection = LinkedStorage.create();
        int elementsNumber = 14000;

        computeStorage(collection, elementsNumber);
    }


    private void computeStorage(WidgetStorage storage, int elementsNumber) {

        final LocalDateTime started = LocalDateTime.now();
        log.info("started filling at {}", started);

        for (int i = 0; i < elementsNumber; i++) {
            storage.add(buildWidgetEntity(i));
        }
        final LocalDateTime finished = LocalDateTime.now();
        log.info("finished filling storage at {}", finished);
        log.info("time diff filling: {} milliseconds",
                Duration.between(started, finished).toMillis());

        assertEquals(elementsNumber, storage.size());

        final LocalDateTime startedAdd = LocalDateTime.now();
        final WidgetEntity added = buildWidgetEntity(elementsNumber / 2);
        storage.add(added);

        final LocalDateTime finishedAdd = LocalDateTime.now();
        log.info("time diff adding 1 element: {} milliseconds",
                Duration.between(startedAdd, finishedAdd).toMillis());

        final LocalDateTime startedFind = LocalDateTime.now();
        assertNotNull(storage.find(added.getId()));

        final LocalDateTime finishedFind = LocalDateTime.now();
        log.info("time diff find 1 element: {} milliseconds",
                Duration.between(startedFind, finishedFind).toMillis());
    }

    @Test
    void testWithFactor() {

        WithFactorStorage storage = WithFactorStorage.create();
        int elementsNumber = 9000000;

        final LocalDateTime started = LocalDateTime.now();
        log.info("started filling at {}", started);

        for (int i = 0; i < elementsNumber; i++) {
            storage.add(buildWidgetEntity(new BigDecimal(i)));
        }
        final LocalDateTime finished = LocalDateTime.now();
        log.info("finished filling storage at {}", finished);
        log.info("time diff filling: {} milliseconds",
                Duration.between(started, finished).toMillis());

        assertEquals(elementsNumber, storage.size());

        final LocalDateTime startedAdd = LocalDateTime.now();
        final WithFactorWidgetEntity added = buildWidgetEntity(new BigDecimal(elementsNumber / 2));
        storage.add(added);

        final LocalDateTime finishedAdd = LocalDateTime.now();
        log.info("time diff adding 1 element: {} milliseconds",
                Duration.between(startedAdd, finishedAdd).toMillis());

        final LocalDateTime startedFind = LocalDateTime.now();
        assertNotNull(storage.find(added.getId()));

        final LocalDateTime finishedFind = LocalDateTime.now();
        log.info("time diff find 1 element: {} milliseconds",
                Duration.between(startedFind, finishedFind).toMillis());
    }

    private void fill(Collection<WidgetEntity> collection, int elementsNumber) {

        final LocalDateTime started = LocalDateTime.now();
        log.info("started filling at {}", started);

        for (int i = 0; i < elementsNumber; i++) {
            collection.add(buildWidgetEntity(i));
        }
        final LocalDateTime finished = LocalDateTime.now();
        log.info("finished filling collection at {}", finished);
        log.info("time diff filling: {} milliseconds",
                Duration.between(started, finished).toMillis());

        assertEquals(elementsNumber, collection.size());

        final LocalDateTime startedAdd = LocalDateTime.now();
        final WidgetEntity added = buildWidgetEntity(1);
        collection.add(added);

        final LocalDateTime finishedAdd = LocalDateTime.now();
        log.info("time diff adding 1 element: {} milliseconds",
                Duration.between(startedAdd, finishedAdd).toMillis());

        final LocalDateTime startedFind = LocalDateTime.now();
        assertTrue(collection.stream().anyMatch(el -> el.equals(added)));

        final LocalDateTime finishedFind = LocalDateTime.now();
        log.info("time diff find 1 element: {} milliseconds",
                Duration.between(startedFind, finishedFind).toMillis());
    }

    private WidgetEntity buildWidgetEntity(int zIndex) {

        final WidgetEntity entity = new WidgetEntity();
        entity.setId(1L);
        entity.setZIndex(zIndex);
        entity.setX(100);
        entity.setY(200);
        entity.setModificationTime(LocalDateTime.now());

        return entity;
    }

    private WithFactorWidgetEntity buildWidgetEntity(BigDecimal z) {

        final WithFactorWidgetEntity entity = new WithFactorWidgetEntity();
        entity.setId(1L);
        entity.setZIndex(z);
        entity.setX(100);
        entity.setY(200);
        entity.setModificationTime(LocalDateTime.now());

        return entity;
    }
}
