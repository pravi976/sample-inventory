package com.fedex.sample.inventory.api.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record InventoryReservationResponse(
        UUID reservationId,
        String orderNumber,
        String status,
        String allocatedWarehouse,
        String expiresIn,
        ReservationLine allocatedLine,
        Map<String, String> audit,
        String reservationRiskToken,
        Instant expiresAt) {
}