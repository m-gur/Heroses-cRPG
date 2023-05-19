package org.gm.hero.services;

import org.gm.hero.entity.Abilities;
import org.gm.hero.entity.AbilitiesAfterModifier;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.ItemType;

import java.util.Map;

public class AbilitiesService {

    private HeroService heroService = new HeroService();
    public AbilitiesAfterModifier setAbilitiesAfterModifier(Hero hero) {
        setAbilitiesFromItems(hero);
                hero.getAbilitiesAfterModifier().setStrength(hero.getAbilities().getStrength() * hero.getModifierAbilities().getStrengthModifier());

                hero.getAbilitiesAfterModifier().setDefence(hero.getAbilities().getDefence() * hero.getModifierAbilities().getDefenceModifier());

                hero.getAbilitiesAfterModifier().setIntelligence(hero.getAbilities().getIntelligence() * hero.getModifierAbilities().getIntelligenceModifier());

                hero.getAbilitiesAfterModifier().setDexterity(hero.getAbilities().getDexterity() * hero.getModifierAbilities().getDexterityModifier());

                hero.getAbilitiesAfterModifier().setAgility(hero.getAbilities().getAgility() * hero.getModifierAbilities().getAgilityModifier());

                hero.getAbilitiesAfterModifier().setSpeed(hero.getAbilities().getSpeed() * hero.getModifierAbilities().getSpeedModifier());
                return hero.getAbilitiesAfterModifier();
    }

    public void distributeSkillPoints(Hero hero, Map<String, Integer> skillPointsDistribution) {
        if (hero.getSkillPoints() <= 0) {
            System.out.println("No skill points available to distribute.");
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

            switch (skillName) {
                case "strength" -> {
                    hero.getAbilities().setStrength(hero.getAbilities().getStrength() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setStrength(hero.getAbilities().getStrength() * hero.getModifierAbilities().getStrengthModifier());
                }
                case "defence" -> {
                    hero.getAbilities().setDefence(hero.getAbilities().getDefence() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setDefence(hero.getAbilities().getDefence() * hero.getModifierAbilities().getDefenceModifier());
                }
                case "intelligence" -> {
                    hero.getAbilities().setIntelligence(hero.getAbilities().getIntelligence() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setIntelligence(hero.getAbilities().getIntelligence() * hero.getModifierAbilities().getIntelligenceModifier());
                }
                case "dexterity" -> {
                    hero.getAbilities().setDexterity(hero.getAbilities().getDexterity() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setDexterity(hero.getAbilities().getDexterity() * hero.getModifierAbilities().getDexterityModifier());
                }
                case "agility" -> {
                    hero.getAbilities().setAgility(hero.getAbilities().getAgility() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setAgility(hero.getAbilities().getAgility() * hero.getModifierAbilities().getAgilityModifier());
                }
                case "speed" -> {
                    hero.getAbilities().setSpeed(hero.getAbilities().getSpeed() + pointsToAdd);
                    hero.getAbilitiesAfterModifier().setSpeed(hero.getAbilities().getSpeed() * hero.getModifierAbilities().getSpeedModifier());
                }
                default -> System.out.println("Invalid skill name: " + skillName);
            }

            remainingSkillPointsToDistribute -= pointsToAdd;
            hero.setSkillPoints(remainingSkillPointsToDistribute);
            hero.setDamage(heroService.setDamage(hero));
            hero.setHp(heroService.setOrReviveHP(hero));
        }

        System.out.println("Skill points distributed successfully.");
    }

    public Abilities setAbilitiesFromItems(Hero hero) {
        float itemsStrength = 0;
        float itemsDefence = 0;
        float itemsIntelligence = 0;
        float itemsDexterity = 0;
        float itemsAgility = 0;
        float itemsSpeed = 0;
        if (hero.getEquippedItems().containsKey(ItemType.HELM)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.HELM).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.ARMOR)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.ARMOR).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.RING)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.RING).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.RING).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.RING).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.RING).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.RING).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.RING).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.NECKLACE)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.NECKLACE).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.TROUSERS)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.TROUSERS).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.SHOES)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.SHOES).getAbilities().getSpeed();
        }

        if (hero.getEquippedItems().containsKey(ItemType.WEAPON)) {
            itemsStrength += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getStrength();
            itemsDefence += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getDefence();
            itemsIntelligence += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getIntelligence();
            itemsDexterity += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getDexterity();
            itemsAgility += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getAgility();
            itemsSpeed += hero.getEquippedItems().get(ItemType.WEAPON).getAbilities().getSpeed();
        }
            hero.getAbilities().setStrength(hero.getAbilities().getStrength() + itemsStrength);
            hero.getAbilities().setDefence(hero.getAbilities().getDefence() + itemsDefence);
            hero.getAbilities().setIntelligence(hero.getAbilities().getIntelligence() + itemsIntelligence);
            hero.getAbilities().setDexterity(hero.getAbilities().getDexterity() + itemsDexterity);
            hero.getAbilities().setAgility(hero.getAbilities().getAgility() + itemsAgility);
            hero.getAbilities().setSpeed(hero.getAbilities().getSpeed() + itemsSpeed);
            return hero.getAbilities();
    }
}
