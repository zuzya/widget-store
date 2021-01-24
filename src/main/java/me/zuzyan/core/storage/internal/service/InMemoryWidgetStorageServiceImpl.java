package me.zuzyan.core.storage.internal.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.config.RelationalDatabaseConfiguration;
import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Component
@ConditionalOnMissingBean(RelationalDatabaseConfiguration.class)
public class InMemoryWidgetStorageServiceImpl implements WidgetStorageService {

    @Autowired
    private WidgetStorage<WidgetEntity> widgetStorage;

    @Override
    public WidgetEntity create(WidgetModel model) {

        WidgetEntity entity = new WidgetEntity(model);
        return save(entity);
    }

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        WidgetEntity found = widgetStorage.find(entity.getId());
        if (found != null) {
            found.incVersion();
            found.setModificationTime(LocalDateTime.now());
        } else {
            widgetStorage.add(entity);
        }

        return entity;
    }

    @Override
    public Optional<WidgetEntity> getById(Long id) {

        return Optional.ofNullable(widgetStorage.find(id));
    }

    @Override
    public Collection<WidgetEntity> getAll() {

        return widgetStorage.findAll();
    }

    @Override
    public void remove(Long id) {

        widgetStorage.delete(id);
    }

    @Override
    public void removeAll() {

        widgetStorage.deleteAll();
    }
}
