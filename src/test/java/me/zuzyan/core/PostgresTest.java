package me.zuzyan.core;

import static me.zuzyan.core.constants.Profile.TEST;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Base test for postgres supported tests
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(TEST)
public abstract class PostgresTest extends PostgresContainerInitializer {
}
