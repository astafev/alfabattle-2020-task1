package ru.astafev.alfabattle.task5.service;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PriceDto {
    String id;
    String groupId;
    String name;
    BigDecimal price;
}
