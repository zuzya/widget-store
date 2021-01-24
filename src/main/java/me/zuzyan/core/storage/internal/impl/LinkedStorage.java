package me.zuzyan.core.storage.internal.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * WidgetStorage based on {@link ConcurrentSkipListSet} with {@link Container} as elements. On
 * "add" operation - calculates the next element and increment z-index if z-indexes are the same.
 * Calculation of next element based on {@link Container#getNext()}.
 * <p>
 * Limitations: with default size of stack memory can provide approximately 14000 elements. Need to
 * tune -Xss parameter
 * </p>
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class LinkedStorage implements WidgetStorage {

    private LinkedStorage() {

        super();
    }

    public static WidgetStorage create() {

        return new LinkedStorage();
    }

    private AtomicLong incrementer = new AtomicLong(0);

    private ConcurrentSkipListSet<Container> collection = new ConcurrentSkipListSet<>();

    @Override
    public void add(WidgetEntity entity) {

        Container toAdd = new Container();
        entity.setId(incrementer.addAndGet(1));
        entity.setCreationTime(LocalDateTime.now());
        entity.setModificationTime(LocalDateTime.now());
        toAdd.setWidget(entity);
        toAdd.setNext(collection.higher(toAdd));
        collection.add(toAdd);

        // write next element link for previous
        Container prev = collection.lower(toAdd);
        if (prev != null) {
            prev.setNext(toAdd);
        }

        if (!collection.isEmpty()) {
            incrementZIndexForNext(toAdd);
        }
    }

    /**
     * Recursively increment z-index for next element if they are the same
     * 
     * @param current
     *            current element of collection
     */
    private void incrementZIndexForNext(Container current) {

        Container next = current.getNext();

        if (next != null) {

            final WidgetEntity widgetOfGreater = next.getWidget();
            if (widgetOfGreater.getZIndex().equals(current.getWidget().getZIndex())) {
                widgetOfGreater.incZ();
            }
            incrementZIndexForNext(next);
        }
    }

    @Override
    public int size() {

        return collection.size();
    }

    @Override
    public WidgetEntity find(Long id) {

        return collection.stream()//
                .map(Container::getWidget)//
                .filter(widget -> widget.getId().equals(id))//
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
    public Collection<WidgetEntity> findAll() {

        return collection.stream()//
                .map(Container::getWidget)//
                .collect(Collectors.toList());
    }

}
