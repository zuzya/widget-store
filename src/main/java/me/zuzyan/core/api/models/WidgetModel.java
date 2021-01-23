package me.zuzyan.core.api.models;

import static me.zuzyan.core.constants.Common.DATE_TIME_FORMAT;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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
@ApiModel(description = "widget data")
public class WidgetModel implements Serializable {

    @ApiModelProperty("widget identifier")
    private Long id;

    @ApiModelProperty("x coordinate")
    @NotNull
    private Integer x;

    @ApiModelProperty("y coordinate")
    @NotNull
    private Integer y;

    @ApiModelProperty("z-index")
    @NotNull
    @JsonProperty("z-index")
    private Integer zIndex;

    @ApiModelProperty("widget width")
    @NotNull
    @Min(1)
    private Integer width;

    @ApiModelProperty("widget height")
    @NotNull
    @Min(1)
    private Integer height;

    @ApiModelProperty("last modification time")
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
