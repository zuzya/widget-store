package me.zuzyan;

import me.zuzyan.core.storage.db.service.JpaWidgetStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.internal.service.InMemoryWidgetStorageServiceImpl;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServiceRunner {

    public static void main(String[] args) {

        SpringApplication.run(ServiceRunner.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(JpaWidgetStorageServiceImpl.class)
    public WidgetStorageService inMemoryWidgetStorageService() {

        return new InMemoryWidgetStorageServiceImpl();
    }

}
