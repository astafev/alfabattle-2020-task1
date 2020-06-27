package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

/**
 * Параметры скидки при покупке связанных товаров
 */
@Data
public class ItemGroupRule {
    private Double discount;
    private String groupId;
    private Integer shopId;

}

