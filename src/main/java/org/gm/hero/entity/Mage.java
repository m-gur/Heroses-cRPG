package org.gm.hero.entity;

import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.MageModifierStrategy;

public class Mage extends Hero {
    public Mage(String name) {
        super(name);
    }
    public Mage() {

    }

    @Override
    public ModifierStrategy getModifierStrategy() {
        return new MageModifierStrategy();
    }

    @Override
    public String getHeroType() {
        return "Mage";
    }
}
