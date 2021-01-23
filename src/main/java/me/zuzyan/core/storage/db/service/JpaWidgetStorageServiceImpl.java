package me.zuzyan.core.storage.db.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.config.RelationalDatabaseConfiguration;
import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.db.repository.WidgetJPARepository;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Service("jpaWidgetStorageService")
@ConditionalOnBean(RelationalDatabaseConfiguration.class)
public class JpaWidgetStorageServiceImpl implements WidgetStorageService {

    @Autowired
    private WidgetJPARepository widgetRepository;

    @Override
    public WidgetEntity create(WidgetModel model) {

        WidgetEntity entity = new WidgetEntity(model);
        return save(entity);
    }

    private void moveZIndexes(Collection<WidgetEntity> others, WidgetEntity saved) {

        WidgetEntity prev = saved;
        boolean incremented = true;
        for (WidgetEntity w : others) {

            if (!incremented) {
                break;
            }

            if (w.getZIndex().equals(prev.getZIndex())) {
                prev = w;

                w.setZIndex(w.getZIndex() + 1);
                widgetRepository.save(w);
            } else {
                incremented = false;
            }
        }
    }

    @Override
    public WidgetEntity save(WidgetEntity entity) {

        final WidgetEntity saved = widgetRepository.save(entity);

        Collection<WidgetEntity> others = widgetRepository.findByZIndex(entity.getZIndex());
        moveZIndexes(others, saved);

        return saved;
    }

    @Override
    public Optional<WidgetEntity> getById(Long id) {

        return widgetRepository.findById(id);
    }

    @Override
    public Collection<WidgetEntity> getAll() {

        return (Collection<WidgetEntity>) widgetRepository.findAll();
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
