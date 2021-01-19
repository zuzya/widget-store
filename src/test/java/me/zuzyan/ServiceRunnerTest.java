package me.zuzyan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static me.zuzyan.core.constants.Profile.TEST;

/**
 * Test for context loads
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServiceRunner.class)
@ActiveProfiles(TEST)
class ServiceRunnerTest {


    /**
     * simple test for load spring-context
     */
    @Test
    @SuppressWarnings("java:S2699")
    void testContextLoads() {

    }
}
