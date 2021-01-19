package me.zuzyan.core.api.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import me.zuzyan.AbstractCommonTest;
import me.zuzyan.core.api.models.CreateWidgetResponseV1;
import me.zuzyan.core.api.models.GetAllWidgetsResponseV1;
import me.zuzyan.core.api.models.UpdateWidgetResponseV1;
import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.store.StorageService;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * Test for {@link WidgetController}
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
class WidgetControllerTest extends AbstractCommonTest {

    public static final String API_V_1_WIDGET = "/api/v1";

    @Autowired
    private WebApplicationContext wac;

    /**
     * Мок MVC
     */
    private MockMvc mockMvc;

    @Autowired
    private StorageService<WidgetEntity> storageService;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        // todo: transactional
        storageService.removeAll();
    }

    @Test
    void testCreateWidget() throws Exception {

        // Given
        WidgetModel request = buildWidget();

        // When
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(API_V_1_WIDGET + "/widget")//
                        .contentType(MediaType.APPLICATION_JSON_VALUE)//
                        .content(jsonMapper.writeValueAsString(request)))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andReturn();

        // Then
        final CreateWidgetResponseV1 widgetModel = jsonMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), CreateWidgetResponseV1.class);
        assertNotNull(widgetModel.getId());
    }

    @Test
    void testGetWidget() throws Exception {

        // Given
        WidgetModel model = new WidgetModel();
        final WidgetEntity widgetEntity = storageService.create(model);

        // When
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_V_1_WIDGET + "/widget/{id}", widgetEntity.getId())//
                        .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andExpect(//
                        MockMvcResultMatchers.jsonPath("$.id").value(widgetEntity.getId()));

        // Then
    }

    @Test
    void testUpdateWidget() throws Exception {

        // Given
        WidgetModel toModification = buildWidget();
        final WidgetEntity widgetEntity = storageService.create(toModification);
        toModification.setId(widgetEntity.getId());
        toModification.setZIndex(3);

        // When
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.put(API_V_1_WIDGET + "/widget")//
                        .contentType(MediaType.APPLICATION_JSON_VALUE)//
                        .content(jsonMapper.writeValueAsString(toModification)))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andExpect(//
                        MockMvcResultMatchers.jsonPath("$.id").value(widgetEntity.getId()))//
                .andReturn();

        // Then
        final UpdateWidgetResponseV1 responseModel = jsonMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), UpdateWidgetResponseV1.class);
        assertEquals(toModification.getZIndex(), responseModel.getZIndex());

        // check entity updated
        final Optional<WidgetEntity> reloaded = storageService.load(widgetEntity.getId());
        assertTrue(reloaded.isPresent());
        assertEquals(toModification.getZIndex(), reloaded.get().getZIndex());
    }

    @Test
    void testDeleteWidget() throws Exception {

        // Given
        final WidgetEntity widgetEntity = storageService.create(buildWidget());
        assertFalse(storageService.loadAll().isEmpty());

        // When
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_V_1_WIDGET + "/widget/{id}", widgetEntity.getId())//
                .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.OK.value()));

        // Then
        assertTrue(storageService.loadAll().isEmpty());
    }

    @Test
    void testFindAllWidgets() throws Exception {

        // Given
        storageService.create(buildWidget());
        storageService.create(buildWidget());
        final List<WidgetEntity> entities = storageService.loadAll();
        assertEquals(2, entities.size());

        // When
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get(API_V_1_WIDGET + "/widgets")//
                        .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andReturn();

        // Then
        final GetAllWidgetsResponseV1 responseV1 = jsonMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), GetAllWidgetsResponseV1.class);
        assertNotNull(responseV1);
        assertEquals(entities.size(), responseV1.getWidgets().size());
    }

    private WidgetModel buildWidget() {

        WidgetModel model = new WidgetModel();
        model.setX(50);
        model.setY(100);
        model.setZIndex(1);
        model.setWidth(200);
        model.setHeight(400);

        return model;
    }
}
