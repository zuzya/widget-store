package me.zuzyan.core.api.models;

import org.junit.Before;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import me.zuzyan.core.WidgetModelHelper;
import me.zuzyan.serialization.AbstractJsonSerializationTest;

import static me.zuzyan.core.TestTags.SERIALIZATION;

/**
 * Serialization test for {@link WidgetModel}
 *
 * @author Denis Zaripov
 * @created 25.01.2021 г.
 */
@Tag(SERIALIZATION)
public class WidgetModelSerializationTest extends AbstractJsonSerializationTest<WidgetModel> {

    @Autowired
    private JacksonTester<WidgetModel> tester;

    @Before
    public void up() {

        super.up(tester);
    }

    @Override
    protected WidgetModel getModel() {

        return WidgetModelHelper.buildWidget();
    }
}
