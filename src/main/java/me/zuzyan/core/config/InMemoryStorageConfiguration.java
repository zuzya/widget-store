package me.zuzyan.core.config;

import me.zuzyan.core.storage.entity.WidgetEntity;
import me.zuzyan.core.storage.internal.impl.Container;
import me.zuzyan.core.storage.internal.impl.LinkedStorage;
import me.zuzyan.core.storage.internal.impl.WithFactorStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.zuzyan.core.storage.internal.impl.SkipListSetStorage;
import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Storage type configuration
 *
 * @author Denis Zaripov
 * @created 23.01.2021 г.
 */
@Configuration
@ConditionalOnMissingBean(RelationalDatabaseConfiguration.class)
public class InMemoryStorageConfiguration {

    @Bean
    @ConditionalOnProperty(name = "storage.algorithm", havingValue = "skiplist")
    public WidgetStorage skipListSetStorage() {

        return SkipListSetStorage.create();
    }

    @Bean
    @ConditionalOnProperty(name = "storage.algorithm", havingValue = "linked")
    public WidgetStorage linkedStorage() {

        return LinkedStorage.create();
    }

    @Bean
    @ConditionalOnProperty(name = "storage.algorithm", havingValue = "withfactor")
    public WidgetStorage withFactorStorage() {

        return WithFactorStorage.create();
    }
}
