package org.gm.hero.items.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.hero.abilities.entity.Abilities;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private String name;
    private ItemType itemType;
    private Abilities abilities;
    private BigDecimal value;
    private int quantity;
    private boolean isUsage;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", itemType=" + itemType +
                ", abilities=" + abilities +
                ", value=" + value +
                ", quantity=" + quantity +
                ", isUsage=" + isUsage +
                '}';
    }
}
