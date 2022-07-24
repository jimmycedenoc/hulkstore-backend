package com.todo1.hulkstore.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INVENTORY")
public class InventoryEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private Long quantity;
    private Double unitValue;
    private String detail;
    @Column(name = "salecode")
    private Long saleCode;
    @Column(name = "productcode")
    private Long productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALECODE", referencedColumnName = "ID", insertable = false, updatable = false)
    private SaleEntity saleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTCODE", referencedColumnName = "ID", insertable = false, updatable = false)
    private ProductEntity productEntity;

    public InventoryEntity() {

    }
    public InventoryEntity(Date date, Long quantity, Long saleCode, Long productCode, Double unitValue, String detail) {
        this.date = date;
        this.quantity = quantity;
        this.saleCode = saleCode;
        this.productCode = productCode;
        this.unitValue = unitValue;
        this.detail = detail;
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

    public Double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Double unitValue) {
        this.unitValue = unitValue;
    }

    public Long getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(Long saleCode) {
        this.saleCode = saleCode;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public SaleEntity getSaleEntity() {
        return saleEntity;
    }

    public void setSaleEntity(SaleEntity saleEntity) {
        this.saleEntity = saleEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
