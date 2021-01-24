package me.zuzyan.core.storage;

import java.util.Collection;
import java.util.Optional;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Service to manage {@link WidgetEntity}
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
public interface WidgetStorageService {

    WidgetEntity create(WidgetModel object);

    WidgetEntity save(WidgetEntity object);

    Optional<WidgetEntity> getById(Long id);

    Collection<WidgetEntity> getAll();

    void remove(Long id);

    void removeAll();
}
