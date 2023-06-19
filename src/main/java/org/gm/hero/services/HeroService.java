package org.gm.hero.services;

import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.entity.*;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class HeroService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String INVALID = "Invalid hero class";

    public float setDamage(Hero hero) {
        float baseDamage = 10f + (hero.getLvl() * 10);
        AbilitiesAfterModifier abilities = hero.getAbilitiesAfterModifier();

        if (hero instanceof Mage) {
            hero.setDamage(baseDamage + (abilities.getIntelligence() * 10));
        } else if (hero instanceof Knight) {
            hero.setDamage(baseDamage + (abilities.getStrength() * 10));
        } else if (hero instanceof Archer) {
            hero.setDamage(baseDamage + (abilities.getDexterity() * 10));
        } else {
            logger.info(INVALID);
        }

        return hero.getDamage();
    }

    public float setHP(Hero hero) {
        float baseHP = 100f + (hero.getLvl() * 10);
        AbilitiesAfterModifier abilities = hero.getAbilitiesAfterModifier();

        if (hero instanceof Mage) {
            hero.setMaxHp(baseHP + (abilities.getDefence() * 13));
        } else if (hero instanceof Knight) {
            hero.setMaxHp(baseHP + (abilities.getDefence() * 20));
        } else if (hero instanceof Archer) {
            hero.setMaxHp(baseHP + (abilities.getDefence() * 15));
        } else {
            logger.info(INVALID);
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
                    logger.info("Hp restored successfully");
                    break;
                }
            }
        }
    }


}
