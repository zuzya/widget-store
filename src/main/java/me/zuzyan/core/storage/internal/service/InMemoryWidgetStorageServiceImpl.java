package me.zuzyan.core.storage.internal.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.storage.WidgetRepository;
import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.db.service.JpaWidgetStorageServiceImpl;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
//@Component("inMemoryWidgetStorageService")
@ConditionalOnMissingBean(JpaWidgetStorageServiceImpl.class)
public class InMemoryWidgetStorageServiceImpl implements WidgetStorageService {

    @Autowired
    private WidgetRepository<WidgetEntity> widgetRepository;

    @Override
    public WidgetEntity create(WidgetModel model) {

        WidgetEntity entity = new WidgetEntity(model);
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
