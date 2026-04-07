package com.fedex.sample.inventory.contracts;

import au.com.dius.pact.provider.junitsupport.State;
import com.fedex.cdc.provider.AbstractCdcLocalPactProviderVerificationTest;
import com.fedex.cdc.provider.CdcPactProviderVerification;
import com.fedex.sample.inventory.domain.InventoryItemEntity;
import com.fedex.sample.inventory.domain.InventoryItemRepository;
import com.fedex.sample.inventory.domain.InventoryReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.nio.file.Path;

@CdcPactProviderVerification(provider = "sample-inventory")
class InventoryProviderContractVerificationTest extends AbstractCdcLocalPactProviderVerificationTest {
    static {
        System.setProperty("PACT_FOLDER", Path.of("..", "sample-orders", "build", "pacts").toAbsolutePath().normalize().toString());
    }

    @Autowired
    private InventoryItemRepository itemRepository;

    @Autowired
    private InventoryReservationRepository reservationRepository;

    @BeforeEach
    void resetData() {
        reservationRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @State("inventory item SKU-LAPTOP-999 exists with regional stock")
    void inventoryItemExistsWithRegionalStock() {
        itemRepository.save(new InventoryItemEntity("SKU-LAPTOP-999", "MEM-01", 25, 18, 7, 3, new BigDecimal("1299.95")));
    }

    @State("inventory reservation can be confirmed for order ORD-2026-00042")
    void reservationCanBeConfirmed() {
        itemRepository.save(new InventoryItemEntity("SKU-LAPTOP-999", "MEM-01", 25, 18, 7, 3, new BigDecimal("1299.95")));
    }
}