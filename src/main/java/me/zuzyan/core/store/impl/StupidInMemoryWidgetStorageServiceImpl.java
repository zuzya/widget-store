package me.zuzyan.core.store.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.store.WidgetRepository;
import me.zuzyan.core.store.WidgetStorageService;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Service
public class StupidInMemoryWidgetStorageServiceImpl implements WidgetStorageService<WidgetEntity> {

    @Autowired
    private WidgetRepository<WidgetEntity> widgetRepository;

    @Override
    public WidgetEntity create(WidgetModel object) {

        WidgetEntity entity = new WidgetEntity(object);

        entity.setId(UUID.randomUUID().toString());
        entity.setCreationTime(LocalDateTime.now());

        final TreeSet<WidgetEntity> all = (TreeSet) widgetRepository.findAll();


        return save(entity);
    }

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        return widgetRepository.save(entity);
    }

    @Override
    public Optional<WidgetEntity> getById(String id) {

        return widgetRepository.findById(id);
    }

    @Override
    public Collection<WidgetEntity> getAll() {

        return widgetRepository.findAll();
    }

    @Override
    public boolean remove(String id) {

        return widgetRepository.removeById(id);
    }

    @Override
    public void removeAll() {

        widgetRepository.removeAll();
    }
}
