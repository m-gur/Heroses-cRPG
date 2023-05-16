package org.gm.hero.services;

import org.gm.hero.entity.Hero;

import java.util.Map;

public class AbilitiesService {

    public void distributeSkillPoints(Hero hero, Map<String, Integer> skillPointsDistribution) {
        if (hero.getSkillPoints() <= 0) {
            System.out.println("No skill points available to distribute.");
        }

        int remainingSkillPointsToDistribute = hero.getSkillPoints();

        for (Map.Entry<String, Integer> entry : skillPointsDistribution.entrySet()) {
            String skillName = entry.getKey();
            Integer pointsToAdd = entry.getValue();

            if (pointsToAdd <= 0) {
                continue; // Pomijamy wartości niepozytywne lub zerowe
            }

            if (remainingSkillPointsToDistribute < pointsToAdd) {
                System.out.println("Not enough skill points available to distribute.");
                break;
            }

            switch (skillName) {
                case "strength":
                    hero.getAbilities().setStrength(hero.getAbilities().getStrength() + pointsToAdd);
                    break;
                case "defence":
                    hero.getAbilities().setDefence(hero.getAbilities().getDefence() + pointsToAdd);
                    break;
                case "intelligence":
                    hero.getAbilities().setIntelligence(hero.getAbilities().getIntelligence() + pointsToAdd);
                    break;
                case "dexterity":
                    hero.getAbilities().setDexterity(hero.getAbilities().getDexterity() + pointsToAdd);
                    break;
                case "agility":
                    hero.getAbilities().setAgility(hero.getAbilities().getAgility() + pointsToAdd);
                    break;
                case "speed":
                    hero.getAbilities().setSpeed(hero.getAbilities().getSpeed() + pointsToAdd);
                    break;
                default:
                    System.out.println("Invalid skill name: " + skillName);
                    break;
            }

            remainingSkillPointsToDistribute -= pointsToAdd;
        }

        hero.setSkillPoints(hero.getSkillPoints() - remainingSkillPointsToDistribute);

        System.out.println("Skill points distributed successfully.");
    }
}
