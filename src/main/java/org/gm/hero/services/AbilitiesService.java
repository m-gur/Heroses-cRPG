package org.gm.hero.services;

import org.gm.hero.entity.*;

import java.util.Map;

public class AbilitiesService {

    private HeroService heroService = new HeroService();
    public AbilitiesAfterModifier setAbilitiesAfterModifier(Hero hero) {
        setAbilitiesFromItems(hero);

        Abilities abilities = hero.getAbilities();
        AbilitiesAfterModifier abilitiesAfterModifier = hero.getAbilitiesAfterModifier();
        ModifierAbilities modifierAbilities = hero.getModifierAbilities();

        abilitiesAfterModifier.setStrength(abilities.getStrength() * modifierAbilities.getStrengthModifier());
        abilitiesAfterModifier.setDefence(abilities.getDefence() * modifierAbilities.getDefenceModifier());
        abilitiesAfterModifier.setIntelligence(abilities.getIntelligence() * modifierAbilities.getIntelligenceModifier());
        abilitiesAfterModifier.setDexterity(abilities.getDexterity() * modifierAbilities.getDexterityModifier());
        abilitiesAfterModifier.setAgility(abilities.getAgility() * modifierAbilities.getAgilityModifier());
        abilitiesAfterModifier.setSpeed(abilities.getSpeed() * modifierAbilities.getSpeedModifier());

        return abilitiesAfterModifier;
    }


    public void distributeSkillPoints(Hero hero, Map<String, Integer> skillPointsDistribution) {
        if (hero.getSkillPoints() <= 0) {
            System.out.println("No skill points available to distribute.");
            return;
        }

        int remainingSkillPointsToDistribute = hero.getSkillPoints();

        for (Map.Entry<String, Integer> entry : skillPointsDistribution.entrySet()) {
            String skillName = entry.getKey();
            Integer pointsToAdd = entry.getValue();

            if (pointsToAdd <= 0) {
                continue;
            }

            if (remainingSkillPointsToDistribute < pointsToAdd) {
                System.out.println("Not enough skill points available to distribute.");
                break;
            }

            Abilities abilities = hero.getAbilities();
            AbilitiesAfterModifier abilitiesAfterModifier = hero.getAbilitiesAfterModifier();
            ModifierAbilities modifierAbilities = hero.getModifierAbilities();

            switch (skillName) {
                case "strength":
                    abilities.setStrength(abilities.getStrength() + pointsToAdd);
                    abilitiesAfterModifier.setStrength(abilities.getStrength() * modifierAbilities.getStrengthModifier());
                    break;
                case "defence":
                    abilities.setDefence(abilities.getDefence() + pointsToAdd);
                    abilitiesAfterModifier.setDefence(abilities.getDefence() * modifierAbilities.getDefenceModifier());
                    break;
                case "intelligence":
                    abilities.setIntelligence(abilities.getIntelligence() + pointsToAdd);
                    abilitiesAfterModifier.setIntelligence(abilities.getIntelligence() * modifierAbilities.getIntelligenceModifier());
                    break;
                case "dexterity":
                    abilities.setDexterity(abilities.getDexterity() + pointsToAdd);
                    abilitiesAfterModifier.setDexterity(abilities.getDexterity() * modifierAbilities.getDexterityModifier());
                    break;
                case "agility":
                    abilities.setAgility(abilities.getAgility() + pointsToAdd);
                    abilitiesAfterModifier.setAgility(abilities.getAgility() * modifierAbilities.getAgilityModifier());
                    break;
                case "speed":
                    abilities.setSpeed(abilities.getSpeed() + pointsToAdd);
                    abilitiesAfterModifier.setSpeed(abilities.getSpeed() * modifierAbilities.getSpeedModifier());
                    break;
                default:
                    System.out.println("Invalid skill name: " + skillName);
                    continue;
            }

            remainingSkillPointsToDistribute -= pointsToAdd;
            hero.setSkillPoints(remainingSkillPointsToDistribute);
            hero.setDamage(heroService.setDamage(hero));
            hero.setHp(heroService.setOrReviveHP(hero));
        }

        System.out.println("Skill points distributed successfully.");
    }


    public Abilities setAbilitiesFromItems(Hero hero) {
        Abilities abilities = hero.getAbilities();
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();

        for (Item item : equippedItems.values()) {
            Abilities itemAbilities = item.getAbilities();
            abilities.setStrength(abilities.getStrength() + itemAbilities.getStrength());
            abilities.setDefence(abilities.getDefence() + itemAbilities.getDefence());
            abilities.setIntelligence(abilities.getIntelligence() + itemAbilities.getIntelligence());
            abilities.setDexterity(abilities.getDexterity() + itemAbilities.getDexterity());
            abilities.setAgility(abilities.getAgility() + itemAbilities.getAgility());
            abilities.setSpeed(abilities.getSpeed() + itemAbilities.getSpeed());
        }

        return abilities;
    }

}
