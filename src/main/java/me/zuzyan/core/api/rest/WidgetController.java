package me.zuzyan.core.api.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.api.models.*;
import me.zuzyan.core.exceptions.InternalErrorException;
import me.zuzyan.core.exceptions.JsonError;
import me.zuzyan.core.storage.WidgetStorageService;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * REST-controller for managing widgets
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
// todo: swagger
public class WidgetController {

    private static final String NOT_FOUND_WIDGET = "not found widget with id ";

    @Autowired
    @Qualifier("inMemoryWidgetStorageService")
    private WidgetStorageService inMemoryWidgetStorageService;

    @GetMapping(value = "/widget/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetWidgetResponseV1 getWidget(//
            @PathVariable(value = "id") //
            @Size(min = 12,
                    max = 12) //
                    Long id) throws Exception {

        return inMemoryWidgetStorageService.getById(id)//
                .map(GetWidgetResponseV1::mapToModel)//
                .orElseThrow(() -> new InternalErrorException(NOT_FOUND_WIDGET + id));
    }

    @PostMapping(value = "/widget",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WidgetModel createWidget(@Valid @RequestBody WidgetModel request) {

        return CreateWidgetResponseV1.mapToModel(//
                inMemoryWidgetStorageService.create(request));
    }

    @PutMapping(value = "/widget",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UpdateWidgetResponseV1 updateWidget(@RequestBody WidgetModel request) throws Exception {

        return UpdateWidgetResponseV1.mapToModel(inMemoryWidgetStorageService.getById(request.getId())//
                .map(w -> w.merge(request))//
                .map(w -> inMemoryWidgetStorageService.save(w)).orElseThrow(
                        () -> new InternalErrorException(NOT_FOUND_WIDGET + request.getId())));
    }

    @GetMapping(value = "/widgets",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetAllWidgetsResponseV1 getAllWidgets() throws Exception {

        return new GetAllWidgetsResponseV1(//
                inMemoryWidgetStorageService.getAll().stream()
                        .map(entity -> WidgetModel.mapToModel(entity, new WidgetModel()))//
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/widget/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWidget(@PathVariable("id") Long id) {

        inMemoryWidgetStorageService.remove(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InternalErrorException.class)
    public JsonError handleValidationExceptions(InternalErrorException ex) {

        return new JsonError(ex.getCode(), ex.getShortMessage(), ex.getData());
    }
}
