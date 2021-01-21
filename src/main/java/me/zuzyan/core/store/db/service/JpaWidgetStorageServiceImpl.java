package me.zuzyan.core.store.db.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import me.zuzyan.core.config.RelationalDatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.store.WidgetStorageService;
import me.zuzyan.core.store.db.repository.WidgetJPARepository;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * In memory storage implementation
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Service("jpaWidgetStorageService")
@ConditionalOnBean(RelationalDatabaseConfiguration.class)
public class JpaWidgetStorageServiceImpl implements WidgetStorageService<WidgetEntity> {

    @Autowired
    private WidgetJPARepository widgetRepository;

    @Override
    public WidgetEntity create(WidgetModel model) {

        WidgetEntity entity = new WidgetEntity(model);
        entity.setCreationTime(LocalDateTime.now());

        Collection<WidgetEntity> others =
                widgetRepository.findByZIndex(model.getZIndex());

        final WidgetEntity saved = save(entity);
        moveZIndexes(others, saved);

        return saved;
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
            }else {
                incremented = false;
            }
        }
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
