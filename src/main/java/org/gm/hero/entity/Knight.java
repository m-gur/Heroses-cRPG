package org.gm.hero.entity;

import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.KnightModifierStrategy;

public class Knight extends Hero {


    public Knight(String name) {
        super(name);
    }

    @Override
    public ModifierStrategy getModifierStrategy() {
        return new KnightModifierStrategy();
    }
}
