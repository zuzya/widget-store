package me.zuzyan;

import static me.zuzyan.core.constants.Profile.TEST;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common test configuration
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(TEST)
abstract public class AbstractCommonTest {

    @Autowired
    @Qualifier("jsonMapper")
    protected ObjectMapper jsonMapper;
}
