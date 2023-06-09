package org.gm.hero.entity;

import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.ArcherModifierStrategy;

public class Archer extends Hero {
    public Archer(String name) {
        super(name);
    }

    public Archer() {

    }

    @Override
    public ModifierStrategy getModifierStrategy() {
        return new ArcherModifierStrategy();
    }

    @Override
    public String getHeroType() {
        return "Archer";
    }
}
