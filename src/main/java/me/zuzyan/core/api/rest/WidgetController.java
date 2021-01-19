package me.zuzyan.core.api.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.api.models.*;
import me.zuzyan.core.store.StorageService;
import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * REST-controller for managing widgets
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
// todo: interface
// todo: swagger
// todo: storage
public class WidgetController {

    @Autowired
    private StorageService<WidgetEntity> storageService;

    @GetMapping(value = "/widget/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetWidgetResponseV1 getWidget(@PathVariable("id") String id) throws Exception {

        return storageService.load(id)//
                .map(GetWidgetResponseV1::mapToModel)//
                // todo: throw exception
                .orElseThrow(() -> new Exception(("not found widget")));
    }

    @PostMapping(value = "/widget",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WidgetModel createWidget(@RequestBody WidgetModel request) {

        return CreateWidgetResponseV1.mapToModel(//
                storageService.create(request));
    }

    @PutMapping(value = "/widget",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UpdateWidgetResponseV1 updateWidget(@RequestBody WidgetModel request) throws Exception {

        return UpdateWidgetResponseV1.mapToModel(storageService.load(request.getId())//
                .map(w -> w.merge(request))//
                .map(w -> storageService.save(w))
                // todo: exception resolver
                .orElseThrow(() -> new Exception(("not found widget"))));
    }

    @GetMapping(value = "/widgets",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetAllWidgetsResponseV1 getAllWidgets() throws Exception {

        return new GetAllWidgetsResponseV1(//
                storageService.loadAll().stream()//
                        .map(entity -> WidgetModel.mapToModel(entity, new WidgetModel()))//
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/widget/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWidget(@PathVariable("id") String id) {

        storageService.remove(id);
    }
}
