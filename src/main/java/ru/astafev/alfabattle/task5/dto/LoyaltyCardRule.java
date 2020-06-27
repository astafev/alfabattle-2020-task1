package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

/**
 * Параметры скидки при предъявлении пенсионного удостоверения или социальной карты
 */
@Data
public class LoyaltyCardRule {
  private Double discount;
  private Integer shopId;
}