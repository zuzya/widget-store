package me.zuzyan.core.api.models;

import static me.zuzyan.core.constants.Common.DATE_TIME_FORMAT;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Model for widget object
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetModel implements Serializable {

    private Long id;

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    @NotNull
    @JsonProperty("z-index")
    private Integer zIndex;

    @NotNull
    @Min(1)
    private Integer width;

    @NotNull
    @Min(1)
    private Integer height;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime modificationTime;

    public static <W extends WidgetModel> W mapToModel(WidgetEntity entity, W model) {

        model.setId(entity.getId());
        model.setX(entity.getX());
        model.setY(entity.getY());
        model.setZIndex(entity.getZIndex());
        model.setWidth(entity.getWidth());
        model.setHeight(entity.getHeight());
        model.setModificationTime(entity.getModificationTime());

        return model;
    }
}
