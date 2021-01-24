package me.zuzyan.core.storage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * Base entity with common fields
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@MappedSuperclass
@Data
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    protected int version = 1;

    @CreationTimestamp
    protected LocalDateTime creationTime;

    @UpdateTimestamp
    protected LocalDateTime modificationTime;

    public void incVersion() {

        this.version++;
    }
}
