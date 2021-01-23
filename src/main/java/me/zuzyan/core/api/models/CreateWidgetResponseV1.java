package me.zuzyan.core.api.models;

import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
public class CreateWidgetResponseV1 extends WidgetModel {

    public static CreateWidgetResponseV1 mapToModel(WidgetEntity entity) {

        return WidgetModel.mapToModel(entity, new CreateWidgetResponseV1());
    }

}
