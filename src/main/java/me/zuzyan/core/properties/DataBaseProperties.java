package me.zuzyan.core.properties;

import lombok.Getter;
import lombok.Setter;
import me.zuzyan.core.config.RelationalDatabaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * DataBase Properties
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@ConfigurationProperties(prefix = "db")
@ConditionalOnBean(RelationalDatabaseConfiguration.class)
@Validated
@Getter
@Setter
public class DataBaseProperties {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String url;

    @NotNull
    private String schema = "public";

    private String catalog;

    @NotNull
    private String dialect;

    @NotNull
    private String driver;

    private String hbm2ddl = "none";

    private String showSql = "false";

    private Long idleTimeout = 600000L;

    private Long maxLifetime = 1800000L;

    private Integer maximumPoolSize = 2;

    private Long connectionTimeout = 30000L;
}
