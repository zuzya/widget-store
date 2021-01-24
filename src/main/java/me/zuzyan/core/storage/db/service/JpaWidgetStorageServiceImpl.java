package me.zuzyan.core.storage.db.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WidgetEntity save(WidgetEntity entity) {

        Collection<WidgetEntity> others = widgetRepository.findByZIndex(entity.getZIndex());
        final WidgetEntity saved = widgetRepository.save(entity);

        moveZIndexes(others, saved);

        return saved;
    }

    /**
     * Naive algorithm for "shifting" z-indexes. Find all records greater than current and
     * increment their z-index.
     * <p>
     * As improvement we can add additional field "section", that will be indicate minimum z-index
     * of group element who ordered consecutively, i.e. without gaps. So we can select only
     * elements with same section and increment it.
     * </p>
     * 
     * @param others
     *            records greater than current
     * @param saved
     *            current record
     */
    private void moveZIndexes(Collection<WidgetEntity> others, WidgetEntity saved) {

        WidgetEntity prev = saved;
        boolean incremented = true;
        for (WidgetEntity current : others) {

            if (!incremented) {
                break;
            }

            if (current.getZIndex().equals(prev.getZIndex())) {
                prev = current;
                current.incZ();
                widgetRepository.save(current);
            } else {
                incremented = false;
            }
        }
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
