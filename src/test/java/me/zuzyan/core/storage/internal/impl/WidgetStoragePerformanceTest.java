package me.zuzyan.core.storage.internal.impl;

import static me.zuzyan.core.TestTags.PERFORMANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Performance test for storage types If you want to play with JVM args you can run test from cmd.
 * JVM params provided by maven-surefire-plugin
 * <p>
 * Example run: mvn test -Dgroups=performance
 * -Dtest=WidgetStoragePerformanceTest#testAddWithStorage -Delements=5000
 * </p>
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Slf4j
@Tag(PERFORMANCE)
class WidgetStoragePerformanceTest {

    @Test
    void testAddWithStorage() {

        compute(SkipListSetStorage.create(), 8000, this::buildWidgetEntity);
    }

    @Test
    void testAddWithLinkedStorage() {

        compute(LinkedStorage.create(), 14000, this::buildWidgetEntity);
    }

    @Test
    void testAddWithFactorStorage() {

        compute(WithFactorStorage.create(), 9000000, i -> buildFactorEntity(new BigDecimal(i)));
    }

    private void compute(WidgetStorage storage, int elements,
            Function<Integer, ? extends WidgetEntity> buildFunction) {

        final String property = System.getProperty("elements");
        if (property != null) {
            elements = Integer.parseInt(property);
        }

        final LocalDateTime started = LocalDateTime.now();
        log.info("started filling at {}", started);

        for (int i = 0; i < elements; i++) {
            storage.add(buildFunction.apply(i));
        }
        final LocalDateTime finished = LocalDateTime.now();
        log.info("finished filling storage at {}", finished);
        log.info("time diff filling: {} milliseconds",
                Duration.between(started, finished).toMillis());

        assertEquals(elements, storage.size());

        final LocalDateTime startedAdd = LocalDateTime.now();
        final WidgetEntity added = buildFunction.apply(elements / 2);
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

    private WidgetEntity buildWidgetEntity(int zIndex) {

        final WidgetEntity entity = new WidgetEntity();
        entity.setId(1L);
        entity.setZIndex(zIndex);
        entity.setX(100);
        entity.setY(200);
        entity.setModificationTime(LocalDateTime.now());

        return entity;
    }

    private WithFactorWidgetEntity buildFactorEntity(BigDecimal z) {

        final WithFactorWidgetEntity entity = new WithFactorWidgetEntity();
        entity.setId(1L);
        entity.setZIndex(1);
        entity.setZIndexEx(z);
        entity.setX(100);
        entity.setY(200);
        entity.setModificationTime(LocalDateTime.now());

        return entity;
    }
}
