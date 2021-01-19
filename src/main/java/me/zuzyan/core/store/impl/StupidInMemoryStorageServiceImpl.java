package me.zuzyan.core.store.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.store.StorageService;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Service
public class StupidInMemoryStorageServiceImpl implements StorageService<WidgetEntity> {

    private List<WidgetEntity> collection = new ArrayList<>();

    @Override
    public WidgetEntity create(WidgetModel object) {

        WidgetEntity entity = new WidgetEntity(object);

        entity.setId(UUID.randomUUID().toString());
        entity.setCreationTime(LocalDateTime.now());

        return save(entity);
    }

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        entity.setModificationTime(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);

        collection.add(entity);

        return entity;
    }

    @Override
    public Optional<WidgetEntity> load(String id) {

        return collection.stream()//
                .filter(w -> w.getId().equals(id))//
                .findFirst();
    }

    @Override
    public boolean remove(String id) {

        return collection.removeIf(w -> w.getId().equals(id));
    }

    @Override
    public List<WidgetEntity> loadAll() {

        if (collection == null) {
            collection = new ArrayList<>();
        }
        return collection;
    }

    @Override
    public void removeAll() {

        collection.clear();
    }
}
