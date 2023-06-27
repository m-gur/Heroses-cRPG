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
public abstract class Item {
    private String name;
    private Abilities abilities;
    private BigDecimal value;
    private int quantity;
    private boolean isUsage;
    private String itemType;

    public abstract String getItemType();

    public Item(String name, Abilities abilities, BigDecimal value, int quantity, boolean isUsage) {
        this.name = name;
        this.abilities = abilities;
        this.value = value;
        this.quantity = quantity;
        this.isUsage = isUsage;
        this.itemType = getItemType();
    }

    @Override
    public String toString() {
        return "Item{" +
               "name='" + name + '\'' +
               ", abilities=" + abilities +
               ", value=" + value +
               ", quantity=" + quantity +
               ", isUsage=" + isUsage +
               '}';
    }
}
