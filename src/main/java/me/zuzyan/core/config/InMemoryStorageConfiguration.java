package me.zuzyan.core.config;

import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.impl.Container;
import me.zuzyan.core.storage.internal.impl.LinkedStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.zuzyan.core.storage.internal.impl.SkipListSetStorage;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 23.01.2021 г.
 */
@Configuration
@ConditionalOnMissingBean(RelationalDatabaseConfiguration.class)
public class InMemoryStorageConfiguration {

    @Bean
    public WidgetStorage<WidgetEntity> skipListSetStorage() {

        return SkipListSetStorage.create();
    }

    @Bean
    public WidgetStorage<Container> linkedStorage() {

        return LinkedStorage.create();
    }
}
