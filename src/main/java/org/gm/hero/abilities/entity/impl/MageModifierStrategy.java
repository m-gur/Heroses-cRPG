package org.gm.hero.abilities.entity.impl;

import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;

public class MageModifierStrategy implements ModifierStrategy {
    @Override
    public void setModifiers(ModifierAbilities modifierAbilities) {
        modifierAbilities.setStrengthModifier(1.0f);
        modifierAbilities.setDefenceModifier(1.0f);
        modifierAbilities.setIntelligenceModifier(1.2f);
        modifierAbilities.setDexterityModifier(1.05f);
        modifierAbilities.setAgilityModifier(1.02f);
        modifierAbilities.setSpeedModifier(1.0f);
    }
}