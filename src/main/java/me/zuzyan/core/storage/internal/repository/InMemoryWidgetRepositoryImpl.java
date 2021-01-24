package me.zuzyan.core.storage.internal.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import me.zuzyan.core.storage.WidgetRepository;
import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
@Repository
public class InMemoryWidgetRepositoryImpl implements WidgetRepository {

    @Autowired
    private WidgetStorage storage;

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        WidgetEntity found = storage.find(entity.getId());
        if (found != null) {
            found.incVersion();
            found.setModificationTime(LocalDateTime.now());
        } else {
            storage.add(entity);
        }

        return entity;
    }

    @Override
    public Optional<WidgetEntity> findById(Long id) {

        return Optional.ofNullable(storage.find(id));
    }

    @Override
    public void deleteById(Long id) {

        storage.delete(id);
    }

    @Override
    public void deleteAll() {

        storage.deleteAll();
    }

    @Override
    public Collection<WidgetEntity> findAll() {

        return storage.findAll();
    }
}
