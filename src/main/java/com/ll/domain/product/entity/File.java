package com.ll.domain.product.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File extends BaseEntity {
    String type;
    String name;
}
