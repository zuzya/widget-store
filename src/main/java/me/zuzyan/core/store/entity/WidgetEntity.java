package me.zuzyan.core.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zuzyan.core.api.models.WidgetModel;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Entity
@Table(name = "widget")
@Data
@NoArgsConstructor
public class WidgetEntity extends AbstractEntity implements Comparable {

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

    public WidgetEntity merge(WidgetModel request) {

        this.x = request.getX();
        this.y = request.getY();
        this.zIndex = request.getZIndex();
        this.width = request.getWidth();
        this.height = request.getHeight();

        return this;
    }

    @Override
    public int compareTo(Object o) {

         if(this.id > ((WidgetEntity) o).getId()){
          return 1;
        }else if(this.id.equals(((WidgetEntity) o).getId())){
             return 0;
         }

         return -1;
    }
}
