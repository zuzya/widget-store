package me.zuzyan.core.storage.internal.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class WithFactorStorage implements WidgetStorage<WithFactorWidgetEntity> {

    private static final AtomicLong incrementer = new AtomicLong(0);

    private static final ConcurrentSkipListSet<WithFactorWidgetEntity> collection =
            new ConcurrentSkipListSet<>();

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
            BigDecimal delta = baseFactor;
            entity.setZIndexEx(floor.getZIndexEx().subtract(delta));
            computeZIndex(entity, delta);
        }

        collection.add(entity);
    }

    private void computeZIndex(WithFactorWidgetEntity entity, BigDecimal delta) {

        WithFactorWidgetEntity higher = collection.floor(entity);
        if (higher != null && higher.getZIndexEx().equals(entity.getZIndexEx())) {
            delta = delta.multiply(baseFactor);
            entity.setZIndexEx(higher.getZIndexEx().add(delta));

            computeZIndex(entity, delta);
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