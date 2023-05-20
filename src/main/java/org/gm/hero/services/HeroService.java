package org.gm.hero.services;

import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.entity.*;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;

import java.util.List;
import java.util.Map;


public class HeroService {

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

    public float setHP(Hero hero) {
        float baseHP = 100f + (hero.getLvl() * 10);
        AbilitiesAfterModifier abilities = hero.getAbilitiesAfterModifier();
        HeroClass heroClass = hero.getHeroClass();

        switch (heroClass) {
            case MAGE -> hero.setMaxHp(baseHP + (abilities.getDefence() * 13));
            case KNIGHT -> hero.setMaxHp(baseHP + (abilities.getDefence() * 20));
            case ARCHER -> hero.setMaxHp(baseHP + (abilities.getDefence() * 15));
            default -> System.out.println("Invalid hero class: " + heroClass);
        }

        return hero.getMaxHp();
    }

    public void restoreHP(Hero hero, String itemName) {
        Map<ItemType, List<Item>> inventory = hero.getInventory();
        List<Item> usableItems = inventory.get(ItemType.USABLE);

        if (usableItems != null) {
            for (Item item : usableItems) {
                if (item.getName().equals(itemName)) {
                    if (item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else {
                        usableItems.remove(item);
                    }
                    hero.setCurrentHp(hero.getMaxHp());
                    break;
                }
            }
        }
    }


}
