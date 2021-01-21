package me.zuzyan.core.store;

import java.util.Collection;
import java.util.Optional;

import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
public interface WidgetRepository<W> {

    WidgetEntity save(WidgetEntity entity);

    Optional<W> findById(Long id);

    Collection<W> findAll();

    void deleteById(Long id);

    void deleteAll();
}
