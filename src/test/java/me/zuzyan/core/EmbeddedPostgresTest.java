package me.zuzyan.core;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.zuzyan.AbstractCommonTest;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@AutoConfigureEmbeddedDatabase
public abstract class EmbeddedPostgresTest extends AbstractCommonTest {
}
