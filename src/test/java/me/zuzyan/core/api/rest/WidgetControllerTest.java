package me.zuzyan.core.api.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import me.zuzyan.AbstractCommonTest;
import me.zuzyan.core.api.models.CreateWidgetResponseV1;
import me.zuzyan.core.api.models.GetAllWidgetsResponseV1;
import me.zuzyan.core.api.models.UpdateWidgetResponseV1;
import me.zuzyan.core.api.models.WidgetModel;
import me.zuzyan.core.exceptions.ExceptionConstants;
import me.zuzyan.core.store.WidgetStorageService;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * Test for {@link WidgetController}
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
class WidgetControllerTest extends AbstractCommonTest {

    public static final String API_V_1_WIDGET = "/api/v1";

    public static final String MUST_NOT_BE_NULL = "must not be null";

    public static final String MUST_BE_GREATER_THAN = "must be greater than or equal to 1";

    @Autowired
    private WebApplicationContext wac;

    /**
     * Мок MVC
     */
    private MockMvc mockMvc;

    @Autowired
    private WidgetStorageService<WidgetEntity> widgetStorageService;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        // todo: transactional
        widgetStorageService.removeAll();
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
    void testCreateWidgetWithEmptyFields() throws Exception {

        // Given
        WidgetModel request = buildWidget();
        request.setX(null);
        request.setY(null);
        request.setZIndex(null);
        request.setWidth(null);
        request.setHeight(null);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post(API_V_1_WIDGET + "/widget")//
                .contentType(MediaType.APPLICATION_JSON_VALUE)//
                .content(jsonMapper.writeValueAsString(request)))//
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))//
                .andExpect(jsonPath("x").value(MUST_NOT_BE_NULL))//
                .andExpect(jsonPath("y").value(MUST_NOT_BE_NULL))//
                .andExpect(jsonPath("zIndex").value(MUST_NOT_BE_NULL))//
                .andExpect(jsonPath("width").value(MUST_NOT_BE_NULL))//
                .andExpect(jsonPath("height").value(MUST_NOT_BE_NULL));
    }

    @Test
    void testCreateWidgetWithNegativeParams() throws Exception {

        // Given
        WidgetModel request = buildWidget();
        request.setWidth(-1);
        request.setHeight(0);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post(API_V_1_WIDGET + "/widget")//
                .contentType(MediaType.APPLICATION_JSON_VALUE)//
                .content(jsonMapper.writeValueAsString(request)))//
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))//
                .andExpect(jsonPath("width").value(MUST_BE_GREATER_THAN))//
                .andExpect(jsonPath("height").value(MUST_BE_GREATER_THAN));
    }

    @Test
    void testGetWidget() throws Exception {

        // Given
        WidgetModel model = new WidgetModel();
        final WidgetEntity widgetEntity = widgetStorageService.create(model);

        // When
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_V_1_WIDGET + "/widget/{id}", widgetEntity.getId())//
                        .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andExpect(//
                        jsonPath("$.id").value(widgetEntity.getId()));
    }

    @Test
    void testGetWidgetWithEmptyParam() throws Exception {

        // When
        mockMvc.perform(MockMvcRequestBuilders.get(API_V_1_WIDGET + "/widget/{id}", "123")//
                .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))//
                .andExpect(jsonPath("code").value(ExceptionConstants.INTERNAL_ERROR.getCode()))//
                .andExpect(
                        jsonPath("message").value(ExceptionConstants.INTERNAL_ERROR.getMessage()))//
                .andExpect(jsonPath("data").exists());
    }

    @Test
    void testUpdateWidget() throws Exception {

        // Given
        WidgetModel toModification = buildWidget();
        final WidgetEntity widgetEntity = widgetStorageService.create(toModification);
        toModification.setId(widgetEntity.getId());
        toModification.setZIndex(3);

        // When
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.put(API_V_1_WIDGET + "/widget")//
                        .contentType(MediaType.APPLICATION_JSON_VALUE)//
                        .content(jsonMapper.writeValueAsString(toModification)))//
                .andExpect(status().is(HttpStatus.OK.value()))//
                .andExpect(//
                        jsonPath("$.id").value(widgetEntity.getId()))//
                .andReturn();

        // Then
        final UpdateWidgetResponseV1 responseModel = jsonMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(), UpdateWidgetResponseV1.class);
        assertEquals(toModification.getZIndex(), responseModel.getZIndex());

        // check entity updated
        final Optional<WidgetEntity> reloaded = widgetStorageService.getById(widgetEntity.getId());
        assertTrue(reloaded.isPresent());
        assertEquals(toModification.getZIndex(), reloaded.get().getZIndex());
    }

    @Test
    void testDeleteWidget() throws Exception {

        // Given
        final WidgetEntity widgetEntity = widgetStorageService.create(buildWidget());
        assertFalse(widgetStorageService.getAll().isEmpty());

        // When
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_V_1_WIDGET + "/widget/{id}", widgetEntity.getId())//
                .contentType(MediaType.APPLICATION_JSON_VALUE))//
                .andExpect(status().is(HttpStatus.OK.value()));

        // Then
        assertTrue(widgetStorageService.getAll().isEmpty());
    }

    @Test
    void testFindAllWidgets() throws Exception {

        // Given
        widgetStorageService.create(buildWidget());
        widgetStorageService.create(buildWidget());
        final Collection<WidgetEntity> entities = widgetStorageService.getAll();
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
