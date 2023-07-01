package org.gm.hero.items;

import org.gm.hero.abilities.entity.Abilities;

import java.math.BigDecimal;

public class Trousers extends Item {
    public Trousers(String name, Abilities abilities, BigDecimal value, int quantity, boolean isUsage) {
        super(name, abilities, value, quantity, isUsage);
    }

    @Override
    public String getItemType() {
        return "Trousers";
    }
}
