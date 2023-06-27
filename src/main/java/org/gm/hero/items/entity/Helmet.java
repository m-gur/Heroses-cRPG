package org.gm.hero.items.entity;

import org.gm.hero.abilities.entity.Abilities;

import java.math.BigDecimal;

public class Helmet extends Item {
    public Helmet(String name, Abilities abilities, BigDecimal value, int quantity, boolean isUsage) {
        super(name, abilities, value, quantity, isUsage);
    }

    @Override
    public String getItemType() {
        return "Helmet";
    }
}
