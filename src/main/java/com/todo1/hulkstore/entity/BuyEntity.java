package com.todo1.hulkstore.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BUY")
public class BuyEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private Long quantity;
    @Column(name = "usercode")
    private Long userCode;
    @Column(name = "productcode")
    private Long productCode;
    private Double unitValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERCODE", referencedColumnName = "ID", insertable = false, updatable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTCODE", referencedColumnName = "ID", insertable = false, updatable = false)
    private ProductEntity productEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "saleEntity")
    private List<InventoryEntity> inventoryEntityList;

    public BuyEntity() {

    }
    public BuyEntity(Date date, Long quantity, Long userCode, Long productCode, Double unitValue) {
        this.date = date;
        this.quantity = quantity;
        this.userCode = userCode;
        this.productCode = productCode;
        this.unitValue = unitValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public Double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Double unitValue) {
        this.unitValue = unitValue;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public List<InventoryEntity> getInventoryEntityList() {
        return inventoryEntityList;
    }

    public void setInventoryEntityList(List<InventoryEntity> inventoryEntityList) {
        this.inventoryEntityList = inventoryEntityList;
    }
}
