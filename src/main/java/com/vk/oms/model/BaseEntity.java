package com.vk.oms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Абстрактная сущность
 */
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    private void setCreatedDate() {
        createdDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return Objects.equals(this.id, other.id);
    }
}
