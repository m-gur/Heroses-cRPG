package org.gm.hero.entity;

import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.ArcherModifierStrategy;

public class Archer extends Hero {


    public Archer(String name) {
        super(name);
    }

    @Override
    public ModifierStrategy getModifierStrategy() {
        return new ArcherModifierStrategy();
    }
}