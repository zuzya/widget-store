package me.zuzyan.core.store.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

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

    private ConcurrentSkipListSet<WidgetEntity> storage = new ConcurrentSkipListSet<>();

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        entity.setModificationTime(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);

        storage.add(entity);

        return entity;
    }

    @Override
    public Optional<WidgetEntity> findById(Long id) {

        return storage.stream()//
                .filter(w -> w.getId().equals(id))//
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {

        storage.removeIf(w -> w.getId().equals(id));
    }

    @Override
    public Collection<WidgetEntity> findAll() {

        return storage;
    }

    @Override
    public void deleteAll() {

        storage.clear();
    }
}
