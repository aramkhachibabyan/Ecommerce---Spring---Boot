package com.smartCode.ecommerce.model.entity.product;

import com.smartCode.ecommerce.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;
}
