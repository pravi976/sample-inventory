package com.fedex.sample.inventory.api.dto;

public record InventoryQuantityResponse(int onHand, int available, int reserved, int backorderThreshold) {
}