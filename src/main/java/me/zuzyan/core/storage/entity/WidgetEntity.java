package me.zuzyan.core.storage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.zuzyan.core.api.models.WidgetModel;

/**
 * Widget entity
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Entity
@Table(name = "widget")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WidgetEntity extends AbstractEntity implements Comparable<WidgetEntity>{

    @Column(name = "x")
    @NotNull(message = "X coordinate is mandatory")
    private Integer x;

    @Column(name = "y")
    @NotNull(message = "Y coordinate is mandatory")
    private Integer y;

    @Column(name = "z_index")
    @NotNull(message = "Z-index is mandatory")
    private Integer zIndex;

    @Column(name = "width")
    @NotNull(message = "Width is mandatory")
    private Integer width;

    @Column(name = "height")
    @NotNull(message = "Height is mandatory")
    private Integer height;

    public WidgetEntity(WidgetModel request) {

        merge(request);
    }

    @Transient
    public WidgetEntity merge(WidgetModel request) {

        this.x = request.getX();
        this.y = request.getY();
        this.zIndex = request.getZIndex();
        this.width = request.getWidth();
        this.height = request.getHeight();

        return this;
    }

    @Transient
    public void incZ() {

        // todo: atomic
        this.zIndex++;
    }

    @Transient
    @Override
    public int compareTo(WidgetEntity o) {

        if (this.getZIndex() > o.getZIndex()) {
            return 1;
        } else if (this.equals(o)) {
            return 0;
        }
        return -1;
    }
}
