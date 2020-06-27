package ru.astafev.alfabattle.task5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astafev.alfabattle.task5.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
