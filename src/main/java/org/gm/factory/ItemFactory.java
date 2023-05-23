package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;

import java.math.BigDecimal;

public class ItemFactory {
    private Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
    private Abilities abilities2 = new Abilities(0f, 0f, 0f, 0f, 0f, 0f);
    public Item createItem(ItemType itemType) {
        switch (itemType){
            case HELM -> {
                return new Item("Helm", ItemType.HELM, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case ARMOR -> {
                return new Item("Armor", ItemType.ARMOR, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case RING -> {
                return new Item("Ring", ItemType.RING, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case NECKLACE -> {
                return new Item("Necklace", ItemType.NECKLACE, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case TROUSERS -> {
                return new Item("Trousers", ItemType.TROUSERS, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case SHOES -> {
                return new Item("Shoes", ItemType.SHOES, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case WEAPON -> {
                return new Item("Weapon", ItemType.WEAPON, abilities, BigDecimal.valueOf(10), 1, true);
            }
            case USUALLY -> {
                return new Item("Photo", ItemType.USUALLY, abilities2, BigDecimal.valueOf(1), 1, false);
            }
            case USABLE -> {
                return new Item("HP Potion", ItemType.USABLE, abilities2, BigDecimal.valueOf(1), 3, false);
            }
            default -> throw new IllegalArgumentException("This type of time does not exist " + itemType);
        }
    }
}
