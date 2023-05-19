package org.gm.hero.services;

import org.gm.hero.entity.*;

import java.util.Map;

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
        hero.setDamage(setDamage(hero));
        hero.setHp(setOrReviveHP(hero));
    }

    private void addSkillPointsAfterLevelUp(Hero hero) {
        int actualHeroSkillPoints = hero.getSkillPoints();
        hero.setSkillPoints(actualHeroSkillPoints+10);
    }

    public ModifierAbilities setModifierAbilities(Hero hero) {
        if (hero.getHeroClass() == HeroClass.MAGE) {
            hero.getModifierAbilities().setStrengthModifier(1.0f);
            hero.getModifierAbilities().setDefenceModifier(1.0f);
            hero.getModifierAbilities().setIntelligenceModifier(1.2f);
            hero.getModifierAbilities().setDexterityModifier(1.05f);
            hero.getModifierAbilities().setAgilityModifier(1.02f);
            hero.getModifierAbilities().setSpeedModifier(1.0f);
        }
        if (hero.getHeroClass() == HeroClass.KNIGHT) {
            hero.getModifierAbilities().setStrengthModifier(1.2f);
            hero.getModifierAbilities().setDefenceModifier(1.1f);
            hero.getModifierAbilities().setIntelligenceModifier(1.0f);
            hero.getModifierAbilities().setDexterityModifier(1.05f);
            hero.getModifierAbilities().setAgilityModifier(1.02f);
            hero.getModifierAbilities().setSpeedModifier(1.5f);
        }
        if (hero.getHeroClass() == HeroClass.ARCHER) {
            hero.getModifierAbilities().setStrengthModifier(1.05f);
            hero.getModifierAbilities().setDefenceModifier(1.05f);
            hero.getModifierAbilities().setIntelligenceModifier(1.0f);
            hero.getModifierAbilities().setDexterityModifier(1.2f);
            hero.getModifierAbilities().setAgilityModifier(1.1f);
            hero.getModifierAbilities().setSpeedModifier(1.05f);
        }
        return hero.getModifierAbilities();
    }

    public float setDamage(Hero hero) {
        if (hero.getHeroClass() == HeroClass.MAGE) {
            hero.setDamage(10 + (hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getIntelligence() * 10));
        }
        if (hero.getHeroClass() == HeroClass.KNIGHT) {
            hero.setDamage(10 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getStrength() * 10));
        }
        if (hero.getHeroClass() == HeroClass.ARCHER) {
            hero.setDamage(10 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDexterity() * 10));
        }
        return hero.getDamage();
    }

    public float setOrReviveHP(Hero hero) {
        if (hero.getHeroClass() == HeroClass.MAGE) {
            hero.setHp(100 + (hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 13));
        }
        if (hero.getHeroClass() == HeroClass.KNIGHT) {
            hero.setHp(100 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 20));
        }
        if (hero.getHeroClass() == HeroClass.ARCHER) {
            hero.setHp(100 +(hero.getLvl() * 10) + (hero.getAbilitiesAfterModifier().getDefence() * 15));
        }
        return hero.getHp();
    }

    public Map<ItemType, Item> equipItem(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        Item currentlyEquippedItem = equippedItems.get(itemType);
        if (currentlyEquippedItem == null) {
            equippedItems.put(itemType, item);
        } else {
            unequipItem(hero, item);
            equippedItems.put(itemType, item);
        }
        return equippedItems;
    }
    public void unequipItem(Hero hero, Item item) {
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        ItemType itemType = item.getItemType();
        equippedItems.remove(itemType);
    }
}
