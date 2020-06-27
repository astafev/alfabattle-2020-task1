package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

/**
 * Параметры скидки формата \&quot;N+k\&quot; (N+k единиц товара по цене N единиц)
 */
@Data
public class ItemCountRule {
    private Integer bonusQuantity;
    private String itemId;
    private Integer shopId;
    private Integer triggerQuantity;

    public Double getDiscount(Integer quantity) {
        if (quantity < triggerQuantity) {
            return 0d;
        }
        var bulks = quantity / (triggerQuantity + bonusQuantity);
        var freeStuff = bulks * bonusQuantity;
        return ((double)freeStuff)/quantity;
    }
}
