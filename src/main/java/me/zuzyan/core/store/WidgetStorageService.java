package me.zuzyan.core.store;

import java.util.Collection;
import java.util.Optional;

import me.zuzyan.core.api.models.WidgetModel;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
public interface WidgetStorageService<W> {

    W create(WidgetModel object);

    W save(W object);

    Optional<W> getById(Long id);

    Collection<W> getAll();

    void remove(Long id);

    void removeAll();
}
