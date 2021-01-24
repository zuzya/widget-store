package me.zuzyan.core.storage.internal.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Base storage implemented on {@link ConcurrentSkipListSet}
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class BaseSkipListStorage implements WidgetStorage {

    protected static final AtomicLong incrementer = new AtomicLong(0);

    protected static final ConcurrentSkipListSet<WidgetEntity> collection =
            new ConcurrentSkipListSet<>();

    @Override
    public void add(WidgetEntity entity) {

        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());
        entity.setModificationTime(LocalDateTime.now());
        collection.add(entity);
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
