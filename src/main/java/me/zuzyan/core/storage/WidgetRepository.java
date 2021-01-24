package me.zuzyan.core.storage;

import java.util.Collection;
import java.util.Optional;

import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
public interface WidgetRepository {

    WidgetEntity save(WidgetEntity entity);

    Optional<WidgetEntity> findById(Long id);

    Collection<WidgetEntity> findAll();

    void deleteById(Long id);

    void deleteAll();
}
