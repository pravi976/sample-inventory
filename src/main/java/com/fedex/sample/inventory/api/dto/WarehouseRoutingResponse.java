package com.fedex.sample.inventory.api.dto;

import java.util.Map;

public record WarehouseRoutingResponse(String primaryWarehouse, String backupWarehouse, String shippingService, Map<String, String> routingTags) {
}