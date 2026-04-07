package com.fedex.sample.inventory.api.dto;

public record ReservationLine(String sku, int quantity, String inventoryClass, MoneyResponse declaredValue) {
}