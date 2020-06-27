package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

import java.util.List;

/**
 * Матрица промо-механик
 */
@Data
public class PromoMatrix {
  private List<ItemCountRule> itemCountRules = null;

  private List<ItemGroupRule> itemGroupRules = null;

  private List<LoyaltyCardRule> loyaltyCardRules = null;
}

