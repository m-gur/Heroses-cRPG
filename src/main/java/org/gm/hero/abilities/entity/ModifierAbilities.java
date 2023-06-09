package org.gm.hero.abilities.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifierAbilities {
    private float strengthModifier;
    private float defenceModifier;
    private float intelligenceModifier;
    private float dexterityModifier;
    private float agilityModifier;
    private float speedModifier;

    @Override
    public String toString() {
        return "ModifierAbilities{" +
               "strengthModifier=" + strengthModifier +
               ", defenceModifier=" + defenceModifier +
               ", intelligenceModifier=" + intelligenceModifier +
               ", dexterityModifier=" + dexterityModifier +
               ", agilityModifier=" + agilityModifier +
               ", speedModifier=" + speedModifier +
               '}';
    }
}
