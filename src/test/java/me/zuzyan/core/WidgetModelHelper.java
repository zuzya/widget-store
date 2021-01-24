package me.zuzyan.core;

import me.zuzyan.core.api.models.WidgetModel;

/**
 * Widget model helper
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
public class WidgetModelHelper {

    public static WidgetModel buildWidget() {

        WidgetModel model = new WidgetModel();
        model.setX(50);
        model.setY(100);
        model.setZIndex(1);
        model.setWidth(200);
        model.setHeight(400);

        return model;
    }
}
