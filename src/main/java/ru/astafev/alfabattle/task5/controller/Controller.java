package ru.astafev.alfabattle.task5.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.astafev.alfabattle.task5.dto.FinalPriceReceipt;
import ru.astafev.alfabattle.task5.dto.PromoMatrix;
import ru.astafev.alfabattle.task5.dto.ShoppingCart;
import ru.astafev.alfabattle.task5.service.DiscountService;

@RestController()
@RequiredArgsConstructor
public class Controller {
    volatile PromoMatrix promoMatrix = null;

    private final DiscountService service;

    @PostMapping("promo")
    public ResponseEntity<Void> promo(@RequestBody PromoMatrix promoMatrix) {
        try{
            service.setPromoMatrix(promoMatrix);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("receipt")
    public FinalPriceReceipt receipt(@RequestBody  ShoppingCart shoppingCart) {
        return service.calculate(shoppingCart);
    }
}
