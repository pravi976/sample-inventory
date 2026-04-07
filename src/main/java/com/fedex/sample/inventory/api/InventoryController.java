package com.fedex.sample.inventory.api;

import com.fedex.sample.inventory.api.dto.InventoryAvailabilityResponse;
import com.fedex.sample.inventory.api.dto.InventoryQuantityResponse;
import com.fedex.sample.inventory.api.dto.InventoryReservationRequest;
import com.fedex.sample.inventory.api.dto.InventoryReservationResponse;
import com.fedex.sample.inventory.api.dto.MoneyResponse;
import com.fedex.sample.inventory.api.dto.ReservationLine;
import com.fedex.sample.inventory.api.dto.WarehouseRoutingResponse;
import com.fedex.sample.inventory.domain.InventoryItemEntity;
import com.fedex.sample.inventory.domain.InventoryItemRepository;
import com.fedex.sample.inventory.domain.InventoryReservationEntity;
import com.fedex.sample.inventory.domain.InventoryReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryItemRepository itemRepository;
    private final InventoryReservationRepository reservationRepository;

    public InventoryController(InventoryItemRepository itemRepository, InventoryReservationRepository reservationRepository) {
        this.itemRepository = itemRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/sku/{sku}/availability")
    public InventoryAvailabilityResponse availability(@PathVariable String sku,
                                                      @RequestParam String warehouse,
                                                      @RequestParam boolean includeAlternates,
                                                      @RequestParam String customerTier) {
        InventoryItemEntity item = itemRepository.findById(sku)
                .orElseGet(() -> new InventoryItemEntity(sku, warehouse, 25, 18, 7, 3, new BigDecimal("1299.95")));
        return new InventoryAvailabilityResponse(
                item.getSku(),
                warehouse,
                item.getAvailable() > 0,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                new InventoryQuantityResponse(item.getOnHand(), item.getAvailable(), item.getReserved(), item.getBackorderThreshold()),
                routing(warehouse, includeAlternates, customerTier),
                new MoneyResponse(item.getReplacementValue(), "USD"),
                item.getRegionalStock(),
                "provider-risk-token",
                Instant.parse("2026-04-07T10:30:00Z"));
    }

    @PostMapping("/reservations")
    public ResponseEntity<InventoryReservationResponse> reserve(@RequestBody InventoryReservationRequest request) {
        UUID reservationId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        Instant expiresAt = Instant.parse("2026-04-07T11:00:00Z").plus(30, ChronoUnit.MINUTES);
        reservationRepository.save(new InventoryReservationEntity(
                reservationId,
                request.orderNumber(),
                request.line().sku(),
                request.line().quantity(),
                "CONFIRMED",
                request.routingPreference().primaryWarehouse(),
                expiresAt));
        InventoryReservationResponse response = new InventoryReservationResponse(
                reservationId,
                request.orderNumber(),
                "CONFIRMED",
                request.routingPreference().primaryWarehouse(),
                "PT30M",
                request.line(),
                Map.of("key", "root.audit-value", "decision", "AUTO_APPROVED", "source", "H2_TEST"),
                "reservation-risk-token",
                expiresAt);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private WarehouseRoutingResponse routing(String warehouse, boolean includeAlternates, String customerTier) {
        return new WarehouseRoutingResponse(
                warehouse,
                includeAlternates ? "MEM-02" : warehouse,
                "GOLD".equals(customerTier) ? "FEDEX_2DAY" : "GROUND",
                Map.of("key", "root.routing.routingTags-value", "zone", "SOUTH", "temperature", "AMBIENT", "sort", "PRIORITY"));
    }
}