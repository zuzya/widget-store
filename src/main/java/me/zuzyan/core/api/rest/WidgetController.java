package me.zuzyan.core.api.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.api.models.*;
import me.zuzyan.core.exceptions.ExceptionConstants;
import me.zuzyan.core.exceptions.InternalErrorException;
import me.zuzyan.core.exceptions.JsonError;
import me.zuzyan.core.storage.WidgetStorageService;

/**
 * REST-controller for managing widgets
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "Controller for widgets managing")
public class WidgetController {

    private static final String NOT_FOUND_WIDGET = "not found widget with id ";

    @Autowired
    private WidgetStorageService widgetStorageService;

    @ApiOperation(value = "Getting widget by id")
    @GetMapping(value = "/widget/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetWidgetResponseV1 getWidget(//
            @ApiParam("widget id") //
            @PathVariable(value = "id") //
            Long id) throws Exception {

        return widgetStorageService.getById(id)//
                .map(GetWidgetResponseV1::mapToModel)//
                .orElseThrow(() -> new InternalErrorException(NOT_FOUND_WIDGET + id));
    }

    @ApiOperation(value = "Create widget")
    @PostMapping(value = "/widget",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WidgetModel createWidget(@ApiParam("widget data") //
    @Valid @RequestBody WidgetModel request) {

        return CreateWidgetResponseV1.mapToModel(//
                widgetStorageService.create(request));
    }

    @ApiOperation(value = "Update widget")
    @PutMapping(value = "/widget/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UpdateWidgetResponseV1 updateWidget(//
            @ApiParam("widget id") //
            @PathVariable(value = "id") Long id, //
            @ApiParam("widget data") //
            @RequestBody WidgetModel request) throws Exception {

        return UpdateWidgetResponseV1.mapToModel(widgetStorageService.getById(id)//
                .map(w -> w.merge(request))//
                .map(w -> widgetStorageService.save(w)).orElseThrow(
                        () -> new InternalErrorException(NOT_FOUND_WIDGET + request.getId())));
    }

    @ApiOperation(value = "Get all widgets")
    @GetMapping(value = "/widgets",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetAllWidgetsResponseV1 getAllWidgets() {

        return new GetAllWidgetsResponseV1(//
                widgetStorageService.getAll().stream()
                        .map(entity -> WidgetModel.mapToModel(entity, new WidgetModel()))//
                        .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Remove widget by id")
    @DeleteMapping(value = "/widget/{id}")
    public ResponseEntity<String> removeWidget(@ApiParam("widget id") //
    @PathVariable("id") Long id) {

        widgetStorageService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonError handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new JsonError(ExceptionConstants.VALIDATION_ERROR.getCode(),
                ExceptionConstants.VALIDATION_ERROR.getMessage(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InternalErrorException.class)
    public JsonError handleValidationExceptions(InternalErrorException ex) {

        return new JsonError(ex.getCode(), ex.getShortMessage(), ex.getData());
    }
}
