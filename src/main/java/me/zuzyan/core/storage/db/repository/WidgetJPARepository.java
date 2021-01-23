package me.zuzyan.core.storage.db.repository;

import java.util.Collection;

import me.zuzyan.core.config.RelationalDatabaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
 @ConditionalOnBean(RelationalDatabaseConfiguration.class)
@Repository
public interface WidgetJPARepository extends CrudRepository<WidgetEntity, Long> {

    @Query("from WidgetEntity where zIndex >= :zIndex order by zIndex asc")
    Collection<WidgetEntity> findByZIndex(@Param("zIndex") Integer zIndex);
}
