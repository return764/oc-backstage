package com.oracleclub.server.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author :RETURN
 * @date :2021/2/23 14:50
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    @Id
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonIgnore
    private Date deletedAt;

    @PrePersist
    protected void prePersist(){
        Date now = new Date();
        if (createdAt == null){
            createdAt = now;
        }
        if (updatedAt == null){
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void preUpdate(){
        updatedAt = new Date();
    }

    @PreRemove
    protected void preRemove(){
        deletedAt = new Date();
    }
}
