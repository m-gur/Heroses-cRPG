package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.items.entity.*;

import java.math.BigDecimal;

public class ItemFactoryTest {
    public Item createFactoryItem(Class itemType) {
        Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        Abilities abilities2 = new Abilities(0f, 0f, 0f, 0f, 0f, 0f);
        if (itemType.equals(Helmet.class)) {
            return new Helmet("Helm", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Chest.class)) {
            return new Chest("Armor", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Ring.class)) {
            return new Ring("Ring", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Necklace.class)) {
            return new Necklace("Necklace", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Trousers.class)) {
            return new Trousers("Trousers", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Shoes.class)) {
            return new Shoes("Shoes", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Weapon.class)) {
            return new Weapon("Weapon", abilities, BigDecimal.valueOf(10), 1, true);
        } else if (itemType.equals(Usually.class)) {
            return new Usually("Photo", abilities2, BigDecimal.valueOf(1), 1, false);
        } else if (itemType.equals(Usable.class)) {
            return new Usable("HP Potion", abilities2, BigDecimal.valueOf(1), 3, false);
        }
        throw new IllegalArgumentException("This type of time does not exist " + itemType);
    }
}
