package ru.astafev.alfabattle.task5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.astafev.alfabattle.task5.dto.FinalPricePosition;
import ru.astafev.alfabattle.task5.dto.FinalPriceReceipt;
import ru.astafev.alfabattle.task5.dto.ItemCountRule;
import ru.astafev.alfabattle.task5.dto.ItemPosition;
import ru.astafev.alfabattle.task5.dto.PromoMatrix;
import ru.astafev.alfabattle.task5.dto.ShoppingCart;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final PriceService priceService;

    private Map<Integer, Map<String, Double>> shopGroupDiscount = Collections.emptyMap();
    private Map<Integer, Map<String, ItemCountRule>> shopItemDiscount = Collections.emptyMap();
    private Map<Integer, Double> shopLoyaltyDiscount = Collections.emptyMap();


    public synchronized void setPromoMatrix(PromoMatrix promoMatrix) {
        shopItemDiscount = new HashMap<>();
        Optional.ofNullable(promoMatrix.getItemCountRules())
                .orElse(Collections.emptyList())
                .forEach(rule -> {
                            shopItemDiscount.putIfAbsent(rule.getShopId(), new HashMap<>());
                            shopItemDiscount.get(rule.getShopId()).put(rule.getItemId(), rule);
                        }
                );

        shopGroupDiscount = new HashMap<>();
        Optional.ofNullable(promoMatrix.getItemGroupRules())
                .orElse(Collections.emptyList())
                .forEach(rule -> {
                    shopGroupDiscount.putIfAbsent(rule.getShopId(), new HashMap<>());
                    shopGroupDiscount.get(rule.getShopId()).put(rule.getGroupId(), rule.getDiscount());
                });

        shopLoyaltyDiscount = new HashMap<>();
        Optional.ofNullable(promoMatrix.getLoyaltyCardRules())
                .orElse(Collections.emptyList())
                .forEach(rule -> shopLoyaltyDiscount.put(rule.getShopId(), rule.getDiscount()));
    }

    public FinalPriceReceipt calculate(ShoppingCart shoppingCart) {
        var result = new FinalPriceReceipt();
        result.setPositions(
                shoppingCart.getPositions().stream().map(
                        position ->
                                getPrice(position, shoppingCart.getShopId(), shoppingCart.getLoyaltyCard())
                ).collect(Collectors.toList())
        );
        return result;
    }


    private FinalPricePosition getPrice(ItemPosition position, Integer shopId, Boolean loaltyCard) {
        var price = priceService.getPrice(position);
        var result = new FinalPricePosition(price.getId(),
                price.getName(),
                price.getPrice(),
                position.getQuantity()
                );
        if (tryLocalDiscount(result, shopId, loaltyCard, price.getGroupId(), position.getQuantity())) {
            return result;
        } else if (tryCommonDiscount(result, loaltyCard, price.getGroupId(), position.getQuantity())) {
            return result;
        } else {
            result.setPrice(result.getRegularPrice());
            return result;
        }

    }

    private boolean tryCommonDiscount(FinalPricePosition position, Boolean loaltyCard, String groupId, Integer quantity) {
        return tryLocalDiscount(position, -1, loaltyCard, groupId, quantity);
    }

    private boolean tryLocalDiscount(FinalPricePosition position, Integer shopId, Boolean loaltyCard,
                                     String groupId, Integer quantity) {
        var groupDiscount =
                Optional.ofNullable(this.shopGroupDiscount.getOrDefault(shopId, Collections.emptyMap()).get(groupId))
                        .orElse(0d);
        var itemDiscount = Optional.ofNullable(this.shopItemDiscount.getOrDefault(shopId, Collections.emptyMap())
                .get(position.getId())).map(rule -> rule.getDiscount(quantity)).orElse(0d);
        var loyalDiscount = Optional.ofNullable(loaltyCard ? this.shopLoyaltyDiscount.get(shopId) : null).orElse(0d);
        var discount = Math.max(groupDiscount, Math.max(itemDiscount, loyalDiscount));
        if (discount > 0) {
            position.setPrice(position.getRegularPrice().multiply(BigDecimal.valueOf(1d - discount)));
            return true;
        } else {
            return false;
        }
    }
}
