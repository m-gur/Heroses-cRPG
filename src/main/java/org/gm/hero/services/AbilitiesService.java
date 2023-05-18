package org.gm.hero.services;

import org.gm.hero.entity.AbilitiesAfterModifier;
import org.gm.hero.entity.Hero;

import java.util.Map;

public class AbilitiesService {

    private HeroService heroService = new HeroService();
    public AbilitiesAfterModifier setAbilitiesAfterModifier(Hero hero) {
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
}
