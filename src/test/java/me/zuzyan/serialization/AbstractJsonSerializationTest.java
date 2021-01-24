package me.zuzyan.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.boot.test.json.ObjectContentAssert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import me.zuzyan.core.config.JsonMapperConfiguration;

/**
 * AbstractJsonSerializationTest
 *
 * @author Denis Zaripov
 * @created 25.01.2021 г.
 */
@RunWith(SpringRunner.class)
@JsonTest
@ContextHierarchy({ @ContextConfiguration(classes = { JsonMapperConfiguration.class }) })
public abstract class AbstractJsonSerializationTest<T> extends AbstractSerializationTest<T> {

    /**
     * @see JacksonTester
     */
    private JacksonTester<T> tester;

    @SuppressWarnings("unchecked")
    protected void up(JacksonTester<T> tester) {

        this.tester = tester;
        super.up(SerializationTestType.JSON);
    }

    @Test
    public void serializationTest() throws IOException {

        if (operationTypes.contains(SerializationOperationTestType.SERIALIZATION)) {
            T model = getModel();
            toJson(model);
        }

    }

    @Test
    public void deserializationTest() throws IOException {

        if (operationTypes.contains(SerializationOperationTestType.DESERIALIZATION)) {
            fromJson();
        }
    }

    @Test
    public void cycleTest() throws IOException {

        if (operationTypes.contains(SerializationOperationTestType.DESERIALIZATION)
                && operationTypes.contains(SerializationOperationTestType.SERIALIZATION)) {
            T model = fromJson();
            toJson(model);
        }
    }

    private T fromJson() throws IOException {

        ObjectContent<T> model = tester.parse(getControlModel());
        ObjectContentAssert objectContentAssert = assertThat(model);
        comparators.entrySet().stream().forEach(entry -> objectContentAssert
                .usingComparatorForType(entry.getValue(), entry.getKey()));

        objectContentAssert.isEqualToComparingFieldByFieldRecursively(getModel());
        return model.getObject();
    }

    private String toJson(T model) throws IOException {

        JsonContent<T> content = tester.write(model);
        assertThat(content).isStrictlyEqualToJson(getControlModel());
        return content.getJson();
    }

}
