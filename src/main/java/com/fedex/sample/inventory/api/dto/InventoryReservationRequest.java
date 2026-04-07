package com.fedex.sample.inventory.api.dto;

import java.util.Map;
import java.util.UUID;

public record InventoryReservationRequest(
        String orderNumber,
        String customerReference,
        UUID requestId,
        ReservationLine line,
        WarehouseRoutingResponse routingPreference,
        Map<String, String> fulfillmentAttributes,
        String fraudReviewToken) {
}