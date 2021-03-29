package com.oracleclub.server.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/2/23 14:50
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name="custom-id",strategy = "com.oracleclub.server.entity.support.CustomIdGenerator")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null){
            createdAt = now;
        }
        if (updatedAt == null){
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void preUpdate(){
        updatedAt = LocalDateTime.now();
    }

    @PreRemove
    protected void preRemove(){
        deletedAt = LocalDateTime.now();
    }
}
