package ru.astafev.alfabattle.task5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astafev.alfabattle.task5.model.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
}
