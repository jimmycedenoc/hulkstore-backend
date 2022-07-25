package com.todo1.hulkstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long stock;
    @Column(name = "categorycode")
    private Long categoryCode;
    private Double unitValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORYCODE", referencedColumnName = "ID", insertable = false, updatable = false)
    @JsonIgnore
    private CategoryEntity categoryEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productEntity")
    @JsonIgnore
    private List<InventoryEntity> inventoryEntityList;

    public ProductEntity() {

    }
    public ProductEntity(String name, Long stock, Long categoryCode, Double unitValue) {
        this.name = name;
        this.stock = stock;
        this.categoryCode = categoryCode;
        this.unitValue = unitValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Double unitValue) {
        this.unitValue = unitValue;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public List<InventoryEntity> getInventoryEntityList() {
        return inventoryEntityList;
    }

    public void setInventoryEntityList(List<InventoryEntity> inventoryEntityList) {
        this.inventoryEntityList = inventoryEntityList;
    }
}
