package me.zuzyan.core.store;

import me.zuzyan.core.api.models.WidgetModel;

import java.util.List;
import java.util.Optional;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
public interface StorageService<W> {


    W create(WidgetModel object);

    W save(W object);

    Optional<W> load(String id);

    boolean remove(String id);

    List<W> loadAll();

    void removeAll();
}
