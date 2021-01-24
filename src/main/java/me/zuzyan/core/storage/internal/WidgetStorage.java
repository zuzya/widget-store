package me.zuzyan.core.storage.internal;

import java.util.Collection;

import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 23.01.2021 г.
 */
public interface WidgetStorage<W extends WidgetEntity> {

    void add(W entity);

    WidgetEntity find(Long id);

    void delete(Long id);

    void deleteAll();

    Collection<W> findAll();

    int size();
}
