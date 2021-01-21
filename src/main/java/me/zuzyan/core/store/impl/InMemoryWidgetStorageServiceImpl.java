package me.zuzyan.core.store.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.store.WidgetRepository;
import me.zuzyan.core.store.WidgetStorageService;
import me.zuzyan.core.store.db.service.JpaWidgetStorageServiceImpl;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Service("inMemoryWidgetStorageService")
@ConditionalOnMissingBean(JpaWidgetStorageServiceImpl.class)
public class InMemoryWidgetStorageServiceImpl implements WidgetStorageService<WidgetEntity> {

    @Autowired
    private WidgetRepository<WidgetEntity> widgetRepository;

    @Override
    public WidgetEntity create(WidgetModel object) {

        WidgetEntity entity = new WidgetEntity(object);

        entity.setId(new Random().nextLong());
        entity.setCreationTime(LocalDateTime.now());

//        final TreeSet<WidgetEntity> all = (TreeSet) widgetRepository.findAll();

        return save(entity);
    }

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        return widgetRepository.save(entity);
    }

    @Override
    public Optional<WidgetEntity> getById(Long id) {

        return widgetRepository.findById(id);
    }

    @Override
    public Collection<WidgetEntity> getAll() {

        return widgetRepository.findAll();
    }

    @Override
    public void remove(Long id) {

        widgetRepository.deleteById(id);
    }

    @Override
    public void removeAll() {

        widgetRepository.deleteAll();
    }
}
