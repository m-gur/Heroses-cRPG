package org.gm.hero.services;

import org.gm.hero.entity.Hero;
import org.gm.hero.entity.HeroClass;
import org.gm.hero.entity.ModifierAbilities;

public class HeroService {

    public float calculateRequiredExperience(int heroLvl) {
        return (float) heroLvl * 100;
    }
    public void gainExperience(Hero hero, float experiencePoints) {
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints+experiencePoints);
        checkLevelUp(hero);
    }

    private void checkLevelUp(Hero hero) {
        while (hero.getExperience() >= hero.getRequiredExperience()) {
                levelUp(hero);
                hero.setExperience(hero.getExperience() - hero.getRequiredExperience());
                hero.setRequiredExperience(calculateRequiredExperience(hero.getLvl()));
        }
    }

    private void levelUp(Hero hero) {
        int actualHeroLvl = hero.getLvl();
        hero.setLvl(actualHeroLvl+1);
        addSkillPointsAfterLevelUp(hero);
        hero.setDamage(setDamage(hero, hero.getHeroClass()));
        hero.setHp(setHP(hero, hero.getHeroClass()));
    }

    private void addSkillPointsAfterLevelUp(Hero hero) {
        int actualHeroSkillPoints = hero.getSkillPoints();
        hero.setSkillPoints(actualHeroSkillPoints+10);
    }

    public ModifierAbilities setModifierAbilities(Hero hero, HeroClass heroClass) {
        if (heroClass == HeroClass.MAGE) {
            hero.getModifierAbilities().setStrengthModifier(1.0f);
            hero.getModifierAbilities().setDefenceModifier(1.0f);
            hero.getModifierAbilities().setIntelligenceModifier(1.2f);
            hero.getModifierAbilities().setDexterityModifier(1.05f);
            hero.getModifierAbilities().setAgilityModifier(1.02f);
            hero.getModifierAbilities().setSpeedModifier(1.0f);
        }
        if (heroClass == HeroClass.KNIGHT) {
            hero.getModifierAbilities().setStrengthModifier(1.2f);
            hero.getModifierAbilities().setDefenceModifier(1.1f);
            hero.getModifierAbilities().setIntelligenceModifier(1.0f);
            hero.getModifierAbilities().setDexterityModifier(1.05f);
            hero.getModifierAbilities().setAgilityModifier(1.02f);
            hero.getModifierAbilities().setSpeedModifier(1.5f);
        }
        if (heroClass == HeroClass.ARCHER) {
            hero.getModifierAbilities().setStrengthModifier(1.05f);
            hero.getModifierAbilities().setDefenceModifier(1.05f);
            hero.getModifierAbilities().setIntelligenceModifier(1.0f);
            hero.getModifierAbilities().setDexterityModifier(1.2f);
            hero.getModifierAbilities().setAgilityModifier(1.1f);
            hero.getModifierAbilities().setSpeedModifier(1.05f);
        }
        return hero.getModifierAbilities();
    }

    public float setDamage(Hero hero, HeroClass heroClass) {
        if (heroClass == HeroClass.MAGE) {
            hero.setDamage(10 + (hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getIntelligence() * 10));
        }
        if (heroClass == HeroClass.KNIGHT) {
            hero.setDamage(10 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getStrength() * 10));
        }
        if (heroClass == HeroClass.ARCHER) {
            hero.setDamage(10 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDexterity() * 10));
        }
        return hero.getDamage();
    }

    public float setHP(Hero hero, HeroClass heroClass) {
        if (heroClass == HeroClass.MAGE) {
            hero.setHp(100 + (hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 13));
        }
        if (heroClass == HeroClass.KNIGHT) {
            hero.setHp(100 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 20));
        }
        if (heroClass == HeroClass.ARCHER) {
            hero.setHp(100 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 15));
        }
        return hero.getHp();
    }
}
