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
public class LinkedStorage implements WidgetStorage<Container> {

    private LinkedStorage() {

        super();
    }

    public static WidgetStorage<Container> create() {

        return new LinkedStorage();
    }

    private AtomicLong incrementer = new AtomicLong(0);

    private ConcurrentSkipListSet<Container> collection = new ConcurrentSkipListSet<>();

    @Override
    public void add(WidgetEntity entity) {

        Container toAdd = new Container();
        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());
        toAdd.setWidget(entity);
        toAdd.setNext(collection.higher(toAdd));

        Container prev = collection.lower(toAdd);
        if (prev != null) {
            prev.setNext(toAdd);
        }

        if (!collection.isEmpty()) {
            moveForward(toAdd);
        }

        collection.add(toAdd);
    }

    private void moveForward(Container current) {

        Container next = current.getNext();

        if (next != null) {

            final WidgetEntity widgetOfGreater = next.getWidget();
            if (widgetOfGreater.getZIndex().equals(current.getWidget().getZIndex())) {
                widgetOfGreater.setModificationTime(LocalDateTime.now());
                widgetOfGreater.incZ();
            }
            moveForward(next);
        }
    }

    @Override
    public int size() {

        return collection.size();
    }

    @Override
    public Container find(Long id) {

        return collection.stream()//
                .filter(el -> el.getWidget().getId().equals(id))//
                .findFirst().orElse(null);
    }

    @Override
    public void delete(Long id) {

        collection.removeIf(c -> c.getWidget().getId().equals(id));
    }

    @Override
    public void deleteAll() {

        collection.clear();
    }

    @Override
    public Collection<Container> findAll() {

        return collection;
    }

}
