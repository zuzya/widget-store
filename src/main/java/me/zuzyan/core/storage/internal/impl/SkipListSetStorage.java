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
public class SkipListSetStorage implements WidgetStorage<WidgetEntity> {

    private static final AtomicLong incrementer = new AtomicLong(0);

    private static final ConcurrentSkipListSet<WidgetEntity> collection =
            new ConcurrentSkipListSet<>();

    private SkipListSetStorage() {

        super();
    }

    public static WidgetStorage<WidgetEntity> create() {

        return new SkipListSetStorage();
    }

    @Override
    public void add(WidgetEntity entity) {

        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());

        if (!collection.isEmpty()) {
            moveForward(entity);
        }

        collection.add(entity);
    }

    private void moveForward(WidgetEntity toAdd) {

        WidgetEntity greater = collection.ceiling(toAdd);
        if (greater != null && greater.equals(toAdd)) {
            greater = collection.higher(toAdd);
        }

        if (greater != null) {

            if (greater.getZIndex().equals(toAdd.getZIndex())) {

                greater.setModificationTime(LocalDateTime.now());
                greater.incZ();
                collection.remove(greater);
                collection.add(greater);
            }

            moveForward(greater);
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
