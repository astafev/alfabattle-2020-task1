package ru.astafev.alfabattle.task5.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Data
public class FinalPricePosition {
    private final String id;

    private final String name;

    private BigDecimal price;

    private final BigDecimal regularPrice;

    @JsonIgnore
    private final int quantity;
}

