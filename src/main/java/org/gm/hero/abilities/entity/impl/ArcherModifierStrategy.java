package org.gm.hero.abilities.entity.impl;

import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;

public class ArcherModifierStrategy implements ModifierStrategy {
    @Override
    public void setModifiers(ModifierAbilities modifierAbilities) {
        modifierAbilities.setStrengthModifier(1.05f);
        modifierAbilities.setDefenceModifier(1.05f);
        modifierAbilities.setIntelligenceModifier(1.0f);
        modifierAbilities.setDexterityModifier(1.2f);
        modifierAbilities.setAgilityModifier(1.1f);
        modifierAbilities.setSpeedModifier(1.05f);
    }
}