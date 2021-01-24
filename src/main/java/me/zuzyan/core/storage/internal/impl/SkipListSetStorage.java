package me.zuzyan.core.storage.internal.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentSkipListSet;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * WidgetStorage based on {@link ConcurrentSkipListSet}. On "add" operation - calculates the next
 * element and increment z-index if z-indexes are the same. Calculation of next element based on
 * {@link ConcurrentSkipListSet#higher(Object)} method.
 * <p>
 * Limitations: with default size of stack memory can provide approximately 8000 elements. Need to
 * tune -Xss parameter
 * </p>
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class SkipListSetStorage extends BaseSkipListStorage {

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
            incrementZIndexForNext(entity);
        }

    }

    /**
     * Increment z-index when higher element's z-index is the same with current
     * 
     * @param current
     *            element to compare
     */
    private void incrementZIndexForNext(WidgetEntity current) {

        WidgetEntity higher = collection.higher(current);
        if (higher != null && higher.getZIndex().equals(current.getZIndex())) {

            higher.setModificationTime(LocalDateTime.now());
            higher.incZ();

            incrementZIndexForNext(higher);
        }
    }

}
