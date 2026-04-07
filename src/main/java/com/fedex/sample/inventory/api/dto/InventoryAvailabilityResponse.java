package com.fedex.sample.inventory.api.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record InventoryAvailabilityResponse(
        String sku,
        String warehouse,
        boolean reservable,
        UUID correlationId,
        InventoryQuantityResponse quantity,
        WarehouseRoutingResponse routing,
        MoneyResponse replacementValue,
        Map<String, String> regionalStock,
        String internalRiskToken,
        Instant calculatedAt) {
}