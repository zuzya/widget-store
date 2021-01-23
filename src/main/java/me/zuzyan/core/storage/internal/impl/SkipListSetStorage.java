package me.zuzyan.core.storage.internal.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class SkipListSetStorage implements WidgetStorage {

    private static final AtomicLong incrementer = new AtomicLong(0);

    private static final ConcurrentSkipListSet<WidgetEntity> collection =
            new ConcurrentSkipListSet<>();

    private SkipListSetStorage() {

        super();
    }

    public static WidgetStorage create() {

        return new SkipListSetStorage();
    }

    @Override
    public void add(WidgetEntity entity) {

        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());
        entity.setModificationTime(LocalDateTime.now());
        collection.add(entity);

        if (!collection.isEmpty()) {
            moveForward(entity);
        }

    }

    /**
     * Increment z-index when higher element's z-index is the same with current
     * 
     * @param current
     *            element to compare
     */
    private void moveForward(WidgetEntity current) {

        WidgetEntity higher = collection.higher(current);
        if (higher != null && higher.getZIndex().equals(current.getZIndex())) {

            higher.setModificationTime(LocalDateTime.now());
            higher.incZ();

            moveForward(higher);
        }
    }

    @Override
    public int size() {

        return collection.size();
    }

    @Override
    public WidgetEntity find(Long id) {

        return collection.stream()//
                .filter(el -> el.getId().equals(id))//
                .findFirst().orElse(null);
    }

    @Override
    public void delete(Long id) {

        collection.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public void deleteAll() {

        collection.clear();
    }

    @Override
    public Collection<WidgetEntity> findAll() {

        return collection;
    }

}
