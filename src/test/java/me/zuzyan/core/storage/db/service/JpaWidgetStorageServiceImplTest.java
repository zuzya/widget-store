package me.zuzyan.core.storage.db.service;

import static me.zuzyan.core.WidgetModelHelper.buildWidget;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.zuzyan.core.PostgresTest;
import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.db.repository.WidgetJPARepository;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Test for {@link JpaWidgetStorageServiceImpl}
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
class JpaWidgetStorageServiceImplTest extends PostgresTest {

    @Autowired
    private WidgetStorageService jpaWidgetStorageService;

    @Autowired
    private WidgetJPARepository widgetRepository;

    @BeforeEach
    void setUp() {

        // fixme: enable transactions for tests
        widgetRepository.deleteAll();
    }

    @Test
    void testSave() {

        // When
        final WidgetEntity entity = jpaWidgetStorageService.create(buildWidget());

        // Then
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getCreationTime());

        assertTrue(widgetRepository.findById(entity.getId()).isPresent());
    }

    @Test
    void testCreateWithSameZIndex() {

        // Given
        WidgetEntity w1 = createWidget(1);
        WidgetEntity w2 = createWidget(2);
        WidgetEntity w3 = createWidget(3);
        WidgetEntity w4 = createWidget(6);

        // When
        WidgetEntity control = createWidget(2);

        // Then
        w1 = jpaWidgetStorageService.getById(w1.getId()).get();
        w2 = jpaWidgetStorageService.getById(w2.getId()).get();
        w3 = jpaWidgetStorageService.getById(w3.getId()).get();
        w4 = jpaWidgetStorageService.getById(w4.getId()).get();

        assertEquals(1, w1.getZIndex());
        assertEquals(2, control.getZIndex());
        assertEquals(3, w2.getZIndex());
        assertEquals(4, w3.getZIndex());
        assertEquals(6, w4.getZIndex());
    }

    @Test
    void testGetById() {

        // Given
        final WidgetEntity entity = widgetRepository.save(new WidgetEntity(buildWidget()));

        // When
        final WidgetEntity found = jpaWidgetStorageService.getById(entity.getId()).get();

        // Then
        assertNotNull(entity);
        assertEquals(found.getId(), entity.getId());
    }

    private WidgetEntity createWidget(int z) {

        final WidgetModel model = buildWidget();
        model.setZIndex(z);
        WidgetEntity first = jpaWidgetStorageService.create(model);
        assertNotNull(first);
        return first;
    }
}
