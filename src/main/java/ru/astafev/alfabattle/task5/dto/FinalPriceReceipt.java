package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Результат расчета корзины
 */
@Data
public class FinalPriceReceipt {
    private BigDecimal discount;
    private List<FinalPricePosition> positions = null;

    public BigDecimal getTotal() {
        return positions.stream()
                .map(finalPricePosition ->
                        finalPricePosition.getPrice().multiply(
                                BigDecimal.valueOf(finalPricePosition.getQuantity())))
                .reduce(new BigDecimal(0),
                        BigDecimal::add);
    }

    private BigDecimal getTotalRegular() {
        return positions.stream()
                .map(finalPricePosition ->
                        finalPricePosition.getRegularPrice().multiply(
                                BigDecimal.valueOf(finalPricePosition.getQuantity())))
                .reduce(new BigDecimal(0),
                        BigDecimal::add);
    }


    public BigDecimal getDiscount() {
        return this.getTotalRegular().subtract(this.getTotal());
    }
}

