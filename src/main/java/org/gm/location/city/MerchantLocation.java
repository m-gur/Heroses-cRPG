package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.items.services.ItemService;
import org.gm.utils.Utils;

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
                case 1 -> chooseAndSellItem(hero, ItemType.HELMET);
                case 2 -> chooseAndSellItem(hero, ItemType.CHEST);
                case 3 -> chooseAndSellItem(hero, ItemType.RING);
                case 4 -> chooseAndSellItem(hero, ItemType.NECKLACE);
                case 5 -> chooseAndSellItem(hero, ItemType.TROUSERS);
                case 6 -> chooseAndSellItem(hero, ItemType.SHOES);
                case 7 -> chooseAndSellItem(hero, ItemType.WEAPON);
                case 8 -> chooseAndSellItem(hero, ItemType.USUALLY);
                case 9 -> chooseAndSellItem(hero, ItemType.USABLE);
                case 10 -> super.explore(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 10);
    }

    private void chooseAndSellItem(Hero hero, ItemType itemType) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().get(itemType);
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
