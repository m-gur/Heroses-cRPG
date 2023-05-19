package org.gm.hero.services;

import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.ArcherModifierStrategy;
import org.gm.hero.abilities.entity.impl.KnightModifierStrategy;
import org.gm.hero.abilities.entity.impl.MageModifierStrategy;
import org.gm.hero.entity.*;


public class HeroService {

    public ModifierAbilities setModifierAbilities(Hero hero) {
        ModifierAbilities modifierAbilities = hero.getModifierAbilities();
        HeroClass heroClass = hero.getHeroClass();

        ModifierStrategy modifierStrategy;

        switch (heroClass) {
            case MAGE -> modifierStrategy = new MageModifierStrategy();
            case KNIGHT -> modifierStrategy = new KnightModifierStrategy();
            case ARCHER -> modifierStrategy = new ArcherModifierStrategy();
            default -> {
                System.out.println("Invalid hero class: " + heroClass);
                return modifierAbilities;
            }
        }

        modifierStrategy.setModifiers(modifierAbilities);

        return modifierAbilities;
    }

    public float setDamage(Hero hero) {
        float baseDamage = 10f + (hero.getLvl() * 10);
        AbilitiesAfterModifier abilities = hero.getAbilitiesAfterModifier();
        HeroClass heroClass = hero.getHeroClass();

        switch (heroClass) {
            case MAGE -> hero.setDamage(baseDamage + (abilities.getIntelligence() * 10));
            case KNIGHT -> hero.setDamage(baseDamage + (abilities.getStrength() * 10));
            case ARCHER -> hero.setDamage(baseDamage + (abilities.getDexterity() * 10));
            default -> System.out.println("Invalid hero class: " + heroClass);
        }

        return hero.getDamage();
    }

    public float setOrReviveHP(Hero hero) {
        float baseHP = 100f + (hero.getLvl() * 10);
        AbilitiesAfterModifier abilities = hero.getAbilitiesAfterModifier();
        HeroClass heroClass = hero.getHeroClass();

        switch (heroClass) {
            case MAGE -> hero.setHp(baseHP + (abilities.getDefence() * 13));
            case KNIGHT -> hero.setHp(baseHP + (abilities.getDefence() * 20));
            case ARCHER -> hero.setHp(baseHP + (abilities.getDefence() * 15));
            default -> System.out.println("Invalid hero class: " + heroClass);
        }

        return hero.getHp();
    }

}
