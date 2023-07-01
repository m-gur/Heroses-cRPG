package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.items.*;
import org.gm.utils.Utils;

import java.util.*;

public class MerchantLocation extends CityLocation {

    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Map<Integer, Class<? extends Item>> itemChoices = new HashMap<>();
        itemChoices.put(1, Helmet.class);
        itemChoices.put(2, Chest.class);
        itemChoices.put(3, Ring.class);
        itemChoices.put(4, Necklace.class);
        itemChoices.put(5, Trousers.class);
        itemChoices.put(6, Shoes.class);
        itemChoices.put(7, Weapon.class);
        itemChoices.put(8, Usually.class);
        itemChoices.put(9, Usable.class);
        itemChoices.put(10, null); // Return option

        do {
            logger.info("""
                What item do you want to sell?
                1. Helmet
                2. Chest
                3. Ring
                4. Necklace
                5. Trousers
                6. Shoes
                7. Weapon
                8. Usually
                9. Usable
                10. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Class<? extends Item> itemClass = itemChoices.get(choice);
            if (itemClass != null && choice != 10) {
                chooseAndSellItem(hero, itemClass);
            } else if (choice == 10) {
                super.explore(hero);
            } else {
                logger.info(INVALID);
            }
        } while (choice != 10);
    }


    private void chooseAndSellItem(Hero hero, Class<? extends Item> itemType) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().getOrDefault(itemType, new ArrayList<>());
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            explore(hero);
        }

        Utils.printItems(items);
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            Item selected = items.get(selectedIndex);
            selected.sellItem(hero);
            logger.info("Item sold: " + selected);
        } else {
            logger.info("Invalid item selection.");
        }
    }
}
