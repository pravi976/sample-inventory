package com.fedex.sample.inventory.api.dto;

import java.math.BigDecimal;

public record MoneyResponse(BigDecimal amount, String currency) {
}