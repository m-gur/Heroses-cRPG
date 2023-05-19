package org.gm.hero.services;

import org.gm.hero.entity.*;

import java.util.Map;

public class HeroService {

    public float calculateRequiredExperience(int heroLevel) {
        return (float) heroLevel * 100;
    }
    public void gainExperience(Hero hero, float experiencePoints) {
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints + experiencePoints);
        checkLevelUp(hero);
    }

    private void checkLevelUp(Hero hero) {
        while (hero.getExperience() >= hero.getRequiredExperience()) {
            levelUp(hero);
            float remainingExperience = hero.getExperience() - hero.getRequiredExperience();
            hero.setExperience(remainingExperience);
            hero.setRequiredExperience(calculateRequiredExperience(hero.getLvl()));
        }
    }

    private void levelUp(Hero hero) {
        int actualHeroLvl = hero.getLvl();
        hero.setLvl(actualHeroLvl + 1);
        addSkillPointsAfterLevelUp(hero);
        hero.setDamage(setDamage(hero));
        hero.setHp(setOrReviveHP(hero));
    }

    private void addSkillPointsAfterLevelUp(Hero hero) {
        int actualHeroSkillPoints = hero.getSkillPoints();
        int additionalSkillPoints = 10;
        hero.setSkillPoints(actualHeroSkillPoints + additionalSkillPoints);
    }


    public ModifierAbilities setModifierAbilities(Hero hero) {
        ModifierAbilities modifierAbilities = hero.getModifierAbilities();
        HeroClass heroClass = hero.getHeroClass();

        switch (heroClass) {
            case MAGE -> {
                modifierAbilities.setStrengthModifier(1.0f);
                modifierAbilities.setDefenceModifier(1.0f);
                modifierAbilities.setIntelligenceModifier(1.2f);
                modifierAbilities.setDexterityModifier(1.05f);
                modifierAbilities.setAgilityModifier(1.02f);
                modifierAbilities.setSpeedModifier(1.0f);
            }
            case KNIGHT -> {
                modifierAbilities.setStrengthModifier(1.2f);
                modifierAbilities.setDefenceModifier(1.1f);
                modifierAbilities.setIntelligenceModifier(1.0f);
                modifierAbilities.setDexterityModifier(1.05f);
                modifierAbilities.setAgilityModifier(1.02f);
                modifierAbilities.setSpeedModifier(1.5f);
            }
            case ARCHER -> {
                modifierAbilities.setStrengthModifier(1.05f);
                modifierAbilities.setDefenceModifier(1.05f);
                modifierAbilities.setIntelligenceModifier(1.0f);
                modifierAbilities.setDexterityModifier(1.2f);
                modifierAbilities.setAgilityModifier(1.1f);
                modifierAbilities.setSpeedModifier(1.05f);
            }
            default -> System.out.println("Invalid hero class: " + heroClass);
        }

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


    public Map<ItemType, Item> equipItem(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        Item currentlyEquippedItem = equippedItems.get(itemType);

        if (currentlyEquippedItem != null) {
            unequipItem(hero, currentlyEquippedItem);
        }

        item.setUsage(true);
        equippedItems.put(itemType, item);

        return equippedItems;
    }
    public void unequipItem(Hero hero, Item item) {
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        ItemType itemType = item.getItemType();
        equippedItems.remove(itemType);
    }
}
