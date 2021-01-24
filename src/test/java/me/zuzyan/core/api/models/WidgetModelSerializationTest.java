package me.zuzyan.core.api.models;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import me.zuzyan.core.WidgetModelHelper;
import me.zuzyan.serialization.AbstractJsonSerializationTest;

/**
 * Serialization test for {@link WidgetModel}
 *
 * @author Denis Zaripov
 * @created 25.01.2021 г.
 */
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
