package me.zuzyan.core.storage.internal.impl;

import me.zuzyan.core.storage.internal.WidgetStorage;

/**
 * Test for {@link me.zuzyan.core.storage.internal.impl.SkipListSetStorage}
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
class SkipListSetStorageTest extends AbstractStorageTest {

    @Override
    protected WidgetStorage getStorage() {

        return SkipListSetStorage.create();
    }
}
