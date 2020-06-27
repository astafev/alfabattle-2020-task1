package ru.astafev.alfabattle.task5.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "groups")
@Data @EqualsAndHashCode(of = "id")
public class Group {
    @Id
    private String id;
    private String name;
}
