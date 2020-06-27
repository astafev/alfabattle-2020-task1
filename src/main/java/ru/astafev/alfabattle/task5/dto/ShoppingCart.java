package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

import java.util.List;

/**
 * Данные о магазине и товарах в корзине
 */
@Data
public class ShoppingCart {
  private Boolean loyaltyCard;

  private List<ItemPosition> positions = null;
  private Integer shopId;

}
