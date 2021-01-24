package me.zuzyan.core.storage.internal.impl;

import javax.validation.constraints.NotNull;

import lombok.Data;
import me.zuzyan.core.storage.entity.WidgetEntity;

/**
 * Supporting container with link to the next element in collection
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Data
public class Container implements Comparable<Container> {

    @NotNull
    private WidgetEntity widget;

    private Container next;

    @Override
    public int compareTo(Container o) {

        return this.getWidget().compareTo(o.getWidget());
    }

}
