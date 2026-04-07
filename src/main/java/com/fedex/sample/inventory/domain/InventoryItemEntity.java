package com.fedex.sample.inventory.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class InventoryItemEntity {
    @Id
    private String sku;
    private String warehouse;
    private int onHand;
    private int available;
    private int reserved;
    private int backorderThreshold;
    private BigDecimal replacementValue;

    @ElementCollection
    private Map<String, String> regionalStock = new LinkedHashMap<>();

    protected InventoryItemEntity() {
    }

    public InventoryItemEntity(String sku, String warehouse, int onHand, int available, int reserved, int backorderThreshold, BigDecimal replacementValue) {
        this.sku = sku;
        this.warehouse = warehouse;
        this.onHand = onHand;
        this.available = available;
        this.reserved = reserved;
        this.backorderThreshold = backorderThreshold;
        this.replacementValue = replacementValue;
        this.regionalStock.put("key", "root.regionalStock-value");
        this.regionalStock.put("MEM-01", "18");
        this.regionalStock.put("MEM-02", "4");
        this.regionalStock.put("ATL-03", "11");
    }

    public String getSku() { return sku; }
    public String getWarehouse() { return warehouse; }
    public int getOnHand() { return onHand; }
    public int getAvailable() { return available; }
    public int getReserved() { return reserved; }
    public int getBackorderThreshold() { return backorderThreshold; }
    public BigDecimal getReplacementValue() { return replacementValue; }
    public Map<String, String> getRegionalStock() { return regionalStock; }
}