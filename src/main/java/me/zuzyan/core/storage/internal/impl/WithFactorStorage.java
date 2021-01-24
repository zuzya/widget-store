package me.zuzyan.core.storage.internal.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * WidgetStorage based on {@link ConcurrentSkipListSet}. As we need order of z-indexes - we can
 * modify z-indexes and save not the same value. Main idea is that we can insert element in between
 * floating point numbers indefinitely! And we don't need to move other elements.
 * <p>
 * Example: between 1 and 2 we can insert 1,1. between 1.1 and 1.2 we can insert 1,11 and so on...
 * </p>
 * <p>
 * Limitations: works only with floating point numbers. But service requires z-index as integer :(
 * </p>
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class WithFactorStorage implements WidgetStorage<WithFactorWidgetEntity> {

    private static final AtomicLong incrementer = new AtomicLong(0);

    private static final ConcurrentSkipListSet<WithFactorWidgetEntity> collection =
            new ConcurrentSkipListSet<>();

    /**
     * Factor to calculate new element offset
     */
    private BigDecimal baseFactor = new BigDecimal("0.1");

    private WithFactorStorage() {

        super();
    }

    public static WithFactorStorage create() {

        return new WithFactorStorage();
    }

    @Override
    public void add(WithFactorWidgetEntity entity) {

        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());
        entity.setModificationTime(LocalDateTime.now());

        WithFactorWidgetEntity floor = collection.floor(entity);

        if (floor != null && floor.getZIndexEx().equals(entity.getZIndexEx())) {
            BigDecimal offset = baseFactor;
            entity.setZIndexEx(floor.getZIndexEx().subtract(offset));
            computeZIndex(entity, offset);
        }

        collection.add(entity);
    }

    /**
     * Compute new z-index value. Multiply offset and baseFactor and add to current z-index value.
     * If we have higher element with the same z-index - repeat recursively
     * 
     * @param current
     *            current element
     * @param offset
     *            current offset
     */
    private void computeZIndex(WithFactorWidgetEntity current, BigDecimal offset) {

        WithFactorWidgetEntity higher = collection.floor(current);
        if (higher != null && higher.getZIndexEx().equals(current.getZIndexEx())) {
            offset = offset.multiply(baseFactor);
            current.setZIndexEx(higher.getZIndexEx().add(offset));

            computeZIndex(current, offset);
        }
    }

    public int size() {

        return collection.size();
    }

    public WithFactorWidgetEntity find(Long id) {

        return collection.stream()//
                .filter(el -> el.getId().equals(id))//
                .findFirst().orElse(null);
    }

    public void delete(Long id) {

        collection.removeIf(c -> c.getId().equals(id));
    }

    public void deleteAll() {

        collection.clear();
    }

    public Collection<WithFactorWidgetEntity> findAll() {

        return collection;
    }

}
