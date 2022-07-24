package com.todo1.hulkstore.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean active;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryEntity")
    private List<ProductEntity> productEntityList;

    public CategoryEntity(){

    }

    public CategoryEntity(String name, boolean active) {
        this.name = name;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }
}
