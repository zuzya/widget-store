package me.zuzyan.core.api.models;

import me.zuzyan.core.store.entity.WidgetEntity;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
public class UpdateWidgetResponseV1 extends WidgetModel {

    public static UpdateWidgetResponseV1 mapToModel(WidgetEntity entity) {

        return WidgetModel.mapToModel(entity, new UpdateWidgetResponseV1());
    }

}
