package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.*;
import org.gm.hero.items.services.ItemService;
import org.gm.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MerchantLocation extends CityLocation {
    private final ItemService itemService = new ItemService();

    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
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

            switch (choice) {
                case 1 -> chooseAndSellItem(hero, Helmet.class);
                case 2 -> chooseAndSellItem(hero, Chest.class);
                case 3 -> chooseAndSellItem(hero, Ring.class);
                case 4 -> chooseAndSellItem(hero, Necklace.class);
                case 5 -> chooseAndSellItem(hero, Trousers.class);
                case 6 -> chooseAndSellItem(hero, Shoes.class);
                case 7 -> chooseAndSellItem(hero, Weapon.class);
                case 8 -> chooseAndSellItem(hero, Usually.class);
                case 9 -> chooseAndSellItem(hero, Usable.class);
                case 10 -> super.explore(hero);
                default -> logger.info(INVALID);
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
            itemService.sellItem(hero, selected);
            logger.info("Item sold: " + selected);
        } else {
            logger.info("Invalid item selection.");
        }
    }
}
