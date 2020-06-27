package ru.astafev.alfabattle.task5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.astafev.alfabattle.task5.dto.ItemPosition;
import ru.astafev.alfabattle.task5.model.Item;
import ru.astafev.alfabattle.task5.repository.ItemRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final ItemRepository itemRepository;

    @Transactional
    public PriceDto getPrice(ItemPosition position) {
        final Item item = itemRepository.getOne(position.getItemId());
        final BigDecimal price = item
                .getPrice()
                .multiply(BigDecimal.valueOf(position.getQuantity()));
        return new PriceDto(
                item.getId(),
                item.getGroup().getId(),
                item.getName(),
                price
        );
    }
}
