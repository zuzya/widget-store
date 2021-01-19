package me.zuzyan.core.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Data
@NoArgsConstructor
public class GetAllWidgetsResponseV1 implements Serializable {

    private List<WidgetModel> widgets;

    public GetAllWidgetsResponseV1(List<WidgetModel> widgets) {

        this.widgets = widgets;
    }

    public List<WidgetModel> getWidgets() {

        if (widgets == null) {
            widgets = new ArrayList<>();
        }
        return widgets;
    }
}
