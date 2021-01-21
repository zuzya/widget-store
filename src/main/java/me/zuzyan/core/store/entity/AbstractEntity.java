package me.zuzyan.core.store.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

/**
 * Descrition
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
    protected int version;

    @CreationTimestamp
    protected LocalDateTime creationTime;

    @UpdateTimestamp
    protected LocalDateTime modificationTime;
}
