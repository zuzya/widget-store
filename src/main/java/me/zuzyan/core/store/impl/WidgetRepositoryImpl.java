package me.zuzyan.core.store.impl;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Repository;

import me.zuzyan.core.store.WidgetRepository;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
@Repository
public class WidgetRepositoryImpl implements WidgetRepository<WidgetEntity> {

    private Set<WidgetEntity> storage =
            Collections.synchronizedSet(new TreeSet<>(Comparator.comparing(WidgetEntity::getId)));

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        entity.setModificationTime(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);

        storage.add(entity);

        return entity;
    }

    @Override
    public Optional<WidgetEntity> findById(String id) {

        return storage.stream()//
                .filter(w -> w.getId().equals(id))//
                .findFirst();
    }

    @Override
    public boolean removeById(String id) {

        return storage.removeIf(w -> w.getId().equals(id));
    }

    @Override
    public Collection<WidgetEntity> findAll() {

        return storage;
    }

    @Override
    public void removeAll() {

        storage.clear();
    }
}
