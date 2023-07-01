package org.gm.hero.items;

import org.gm.hero.abilities.entity.Abilities;

import java.math.BigDecimal;

public class Ring extends Item {
    public Ring(String name, Abilities abilities, BigDecimal value, int quantity, boolean isUsage) {
        super(name, abilities, value, quantity, isUsage);
    }

    @Override
    public String getItemType() {
        return "Ring";
    }
}
