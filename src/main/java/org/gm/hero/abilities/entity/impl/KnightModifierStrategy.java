package org.gm.hero.abilities.entity.impl;

import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;

public class KnightModifierStrategy implements ModifierStrategy {
    @Override
    public void setModifiers(ModifierAbilities modifierAbilities) {
        modifierAbilities.setStrengthModifier(1.2f);
        modifierAbilities.setDefenceModifier(1.1f);
        modifierAbilities.setIntelligenceModifier(1.0f);
        modifierAbilities.setDexterityModifier(1.05f);
        modifierAbilities.setAgilityModifier(1.02f);
        modifierAbilities.setSpeedModifier(1.5f);
    }
}