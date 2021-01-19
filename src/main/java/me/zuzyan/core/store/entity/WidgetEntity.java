package me.zuzyan.core.store.entity;

import javax.validation.constraints.NotNull;

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

    @NotNull(message = "X coordinate is mandatory")
    private Integer x;

    @NotNull(message = "Y coordinate is mandatory")
    private Integer y;

    @NotNull(message = "Z-index is mandatory")
    private Integer zIndex;

    @NotNull(message = "Width is mandatory")
    private Integer width;

    @NotNull(message = "Height is mandatory")
    private Integer height;

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
