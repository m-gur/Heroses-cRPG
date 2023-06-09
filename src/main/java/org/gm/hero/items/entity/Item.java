package org.gm.hero.items.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gm.hero.abilities.entity.Abilities;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private ItemType itemType;
    private Abilities abilities;
    private BigDecimal value;
    private int quantity;
    private boolean isUsage;

    public Item(String name, ItemType itemType, BigDecimal value, int quantity, boolean isUsage) {
        this.name = name;
        this.itemType = itemType;
        this.value = value;
        this.quantity = quantity;
        this.isUsage = isUsage;
    }

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
