package ru.astafev.alfabattle.task5.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data @EqualsAndHashCode(of = "id")
public class Item {
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    private BigDecimal price;

    public void setGroupId(String id) {
        var group = new Group();
        group.setId(id);
        this.group = group;
    }
}
