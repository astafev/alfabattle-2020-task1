
package ru.astafev.alfabattle.task5.dto;

import lombok.Data;

/**
 * Позиция товара в корзине
 */
@Data
public class ItemPosition {
  private String itemId;
  private Integer quantity;
}

