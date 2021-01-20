package me.zuzyan.core.store;

import java.util.Collection;
import java.util.Optional;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
public interface WidgetRepository<W> {

    W save(W object);

    Optional<W> findById(String id);

    Collection<W> findAll();

    boolean removeById(String id);

    void removeAll();
}
