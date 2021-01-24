package me.zuzyan.serialization;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.fail;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

/**
 * AbstractSerializationTest
 *
 * @author Denis Zaripov
 * @created 25.01.2021 г.
 */
public abstract class AbstractSerializationTest<T> {

    private static final String MODEL_RESOURCE_TEMPLATE =
            "serialization/{type}/{model-file}.{type}";

    protected static Set<SerializationOperationTestType> operationTypes =
            Sets.immutableEnumSet(SerializationOperationTestType.SERIALIZATION,
                    SerializationOperationTestType.DESERIALIZATION);

    @Autowired
    protected ApplicationContext context;

    protected Class<T> clazz;

    private Resource resource;

    protected Map<Class<?>, Comparator> comparators = new HashMap<>();

    protected void up(SerializationTestType type) {

        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        String resourceLocation =
                MODEL_RESOURCE_TEMPLATE.replace("{type}", type.name().toLowerCase())
                        .replace("{model-file}", getModelFile());
        resource = context.getResource(resourceLocation);
    }

    protected String getControlModel() {

        if (resource == null) {
            fail("not found model file");
        }
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toString(inputStream, UTF_8);
        } catch (Exception e) {
            fail("error read model file");
            return null;
        }
    }

    protected abstract T getModel();

    protected String getModelFile() {

        return clazz.getSimpleName();
    }

    protected String[] getProperties() {

        Set<String> properties = new HashSet<>();
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor descriptor : descriptors) {
            String propertyName = descriptor.getName();
            Method method = descriptor.getReadMethod();
            Field field = ReflectionUtils.findField(clazz, propertyName);
            if (isNotIgnored(method) && (field == null || isNotIgnored(field))) {
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[properties.size()]);
    }

    private boolean isNotIgnored(AccessibleObject property) {

        return !property.isAnnotationPresent(JsonIgnore.class);
    }
}
