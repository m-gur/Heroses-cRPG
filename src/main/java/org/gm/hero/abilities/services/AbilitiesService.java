package org.gm.hero.abilities.services;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.abilities.entity.impl.ArcherModifierStrategy;
import org.gm.hero.abilities.entity.impl.KnightModifierStrategy;
import org.gm.hero.abilities.entity.impl.MageModifierStrategy;
import org.gm.hero.entity.*;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.services.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AbilitiesService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
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
        hero.setDamage(heroService.setDamage(hero));
        hero.setMaxHp(heroService.setHP(hero));
        return abilitiesAfterModifier;
    }

    public void distributeSkillPoints(Hero hero, Map<String, Integer> skillPointsDistribution) {
        if (hero.getSkillPoints() <= 0) {
            logger.info("No skill points available to distribute.");
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
                logger.info("Not enough skill points available to distribute.");
                break;
            }

            Abilities abilities = hero.getAbilities();
            AbilitiesAfterModifier abilitiesAfterModifier = hero.getAbilitiesAfterModifier();
            ModifierAbilities modifierAbilities = hero.getModifierAbilities();

            switch (skillName) {
                case "strength" -> {
                    abilities.setStrength(abilities.getStrength() + pointsToAdd);
                    abilitiesAfterModifier.setStrength(abilities.getStrength() * modifierAbilities.getStrengthModifier());
                }
                case "defence" -> {
                    abilities.setDefence(abilities.getDefence() + pointsToAdd);
                    abilitiesAfterModifier.setDefence(abilities.getDefence() * modifierAbilities.getDefenceModifier());
                }
                case "intelligence" -> {
                    abilities.setIntelligence(abilities.getIntelligence() + pointsToAdd);
                    abilitiesAfterModifier.setIntelligence(abilities.getIntelligence() * modifierAbilities.getIntelligenceModifier());
                }
                case "dexterity" -> {
                    abilities.setDexterity(abilities.getDexterity() + pointsToAdd);
                    abilitiesAfterModifier.setDexterity(abilities.getDexterity() * modifierAbilities.getDexterityModifier());
                }
                case "agility" -> {
                    abilities.setAgility(abilities.getAgility() + pointsToAdd);
                    abilitiesAfterModifier.setAgility(abilities.getAgility() * modifierAbilities.getAgilityModifier());
                }
                case "speed" -> {
                    abilities.setSpeed(abilities.getSpeed() + pointsToAdd);
                    abilitiesAfterModifier.setSpeed(abilities.getSpeed() * modifierAbilities.getSpeedModifier());
                }
                default -> {
                    logger.info("Invalid skill name: " + skillName);
                    continue;
                }
            }

            remainingSkillPointsToDistribute -= pointsToAdd;
            hero.setSkillPoints(remainingSkillPointsToDistribute);
            hero.setDamage(heroService.setDamage(hero));
            hero.setMaxHp(heroService.setHP(hero));
        }

        logger.info("Skill points distributed successfully.");
    }

    public void resetSkillPoints(Hero hero) {
        Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        hero.setAbilities(abilities);
        hero.setSkillPoints(hero.getLvl() * 10);
        setAbilitiesAfterModifier(hero);
        logger.info("Skill points reset successfully.");
    }

    public ModifierAbilities setModifierAbilities(Hero hero) {
        ModifierAbilities modifierAbilities = hero.getModifierAbilities();

        ModifierStrategy modifierStrategy;

        if (hero instanceof Mage) {
            modifierStrategy = new MageModifierStrategy();
        } else if (hero instanceof Knight) {
            modifierStrategy = new KnightModifierStrategy();
        } else if (hero instanceof Archer) {
            modifierStrategy = new ArcherModifierStrategy();
        } else {
            logger.info("Invalid hero class");
            return modifierAbilities;
        }

        modifierStrategy.setModifiers(modifierAbilities);

        return modifierAbilities;
    }

    private void setAbilitiesFromItems(Hero hero) {
        Abilities abilities = hero.getAbilities();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();

        for (Item item : equippedItems.values()) {
            Abilities itemAbilities = item.getAbilities();
            abilities.setStrength(abilities.getStrength() + itemAbilities.getStrength());
            abilities.setDefence(abilities.getDefence() + itemAbilities.getDefence());
            abilities.setIntelligence(abilities.getIntelligence() + itemAbilities.getIntelligence());
            abilities.setDexterity(abilities.getDexterity() + itemAbilities.getDexterity());
            abilities.setAgility(abilities.getAgility() + itemAbilities.getAgility());
            abilities.setSpeed(abilities.getSpeed() + itemAbilities.getSpeed());
        }
    }

    public void unsetAbilitiesFromItems(Hero hero, Item item) {
        Abilities abilities = hero.getAbilities();
        if (!item.isUsage()) {
            Abilities itemAbilities = item.getAbilities();
            abilities.setStrength(abilities.getStrength() - itemAbilities.getStrength());
            abilities.setDefence(abilities.getDefence() - itemAbilities.getDefence());
            abilities.setIntelligence(abilities.getIntelligence() - itemAbilities.getIntelligence());
            abilities.setDexterity(abilities.getDexterity() - itemAbilities.getDexterity());
            abilities.setAgility(abilities.getAgility() - itemAbilities.getAgility());
            abilities.setSpeed(abilities.getSpeed() - itemAbilities.getSpeed());
        }
    }
}
