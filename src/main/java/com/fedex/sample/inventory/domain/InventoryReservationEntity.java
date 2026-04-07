package com.fedex.sample.inventory.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class InventoryReservationEntity {
    @Id
    private UUID id;
    private String orderNumber;
    private String sku;
    private int quantity;
    private String status;
    private String allocatedWarehouse;
    private Instant expiresAt;

    @ElementCollection
    private Map<String, String> audit = new LinkedHashMap<>();

    protected InventoryReservationEntity() {
    }

    public InventoryReservationEntity(UUID id, String orderNumber, String sku, int quantity, String status, String allocatedWarehouse, Instant expiresAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.sku = sku;
        this.quantity = quantity;
        this.status = status;
        this.allocatedWarehouse = allocatedWarehouse;
        this.expiresAt = expiresAt;
        this.audit.put("decision", "AUTO_APPROVED");
        this.audit.put("inventoryClass", "SERIALIZED");
    }
}