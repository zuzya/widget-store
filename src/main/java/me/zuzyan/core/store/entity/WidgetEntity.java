package me.zuzyan.core.store.entity;

import lombok.Data;
import me.zuzyan.core.api.models.WidgetModel;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
public class WidgetEntity extends AbstractEntity {

    private int x;

    private int y;

    private int zIndex;

    private int width;

    private int height;

    public WidgetEntity(WidgetModel request) {

        merge(request);
    }

    public WidgetEntity merge(WidgetModel request) {

        this.x = request.getX();
        this.y = request.getY();
        this.zIndex = request.getZIndex();
        this.width = request.getWidth();
        this.height = request.getHeight();

        return this;
    }
}
