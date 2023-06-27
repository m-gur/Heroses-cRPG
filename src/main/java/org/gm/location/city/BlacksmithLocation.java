package org.gm.location.city;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.*;
import org.gm.hero.items.services.ItemService;
import org.gm.utils.Utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BlacksmithLocation extends CityLocation {
    private final ItemService itemService = new ItemService();
    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        logger.info("""
                Welcome adventurer, my name is Greg.
                What brings you to me?
                Are you want to upgrade your equipment?
                """);
        logger.info("Welcome Greg, my name is " + hero.getName());
        do {
            logger.info("""
                    1. Yes, I want to upgrade equipment
                    2. No, only want to ask you about the town
                    3. No, sorry I got lost, thanks (leaving)
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> upgradeEquipment(hero);
                case 2 -> logger.info("""
                        This town is no longer like before..
                        Many things are changed, people changed.
                        Now in city it is not safe, everyone are scared.
                        No one are helping us with the monsters, there was some adventurers.
                        Just like you, people paid them to kill monsters and safe them.
                        After getting money, they run away. The problem wasn't solved
                        and we lost the money..
                        Maybe you want to help us with monsters?
                        Probably if you will give us the proof, monsters died,
                        we will give you some coins for your help.
                        Think about this, on bulletin board should be an announcement.
                        """);
                case 3 -> super.explore(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 3);
    }

    private void upgradeEquipment(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        logger.info("""
                What you want to upgrade?
                To upgrade item you need to pay 10 coins.
                Upgraded item will receive all stats + 1
                1. Helm
                2. Chest
                3. Ring
                4. Necklace
                5. Trousers
                6. Shoes
                7. Weapon
                8. Return
                """);
        do {
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> chooseAndUpgradeItem(hero, Helmet.class);
                case 2 -> chooseAndUpgradeItem(hero, Chest.class);
                case 3 -> chooseAndUpgradeItem(hero, Ring.class);
                case 4 -> chooseAndUpgradeItem(hero, Necklace.class);
                case 5 -> chooseAndUpgradeItem(hero, Trousers.class);
                case 6 -> chooseAndUpgradeItem(hero, Shoes.class);
                case 7 -> chooseAndUpgradeItem(hero, Weapon.class);
                case 8 -> explore(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 8);
    }

    private void chooseAndUpgradeItem(Hero hero, Class<? extends Item> itemType) {
        if (hero.getCoins().compareTo(BigDecimal.valueOf(10)) < 0) {
            logger.info("Invalid needed coins to upgrade item.");
            upgradeEquipment(hero);
        }
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().get(itemType);
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            upgradeEquipment(hero);
        }

        Utils.printItems(items);
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            Item selected = items.get(selectedIndex);
            upgradeItem(hero, selected);
        } else {
            logger.info("Invalid item selection.");
        }
    }

    private void upgradeItem(Hero hero, Item item) {
        Class<? extends Item> itemType = item.getClass();
        List<Item> items = hero.getInventory().get(itemType);
        if (hero.getEquippedItems().get(itemType) != null) {
            Item item1 = hero.getEquippedItems().get(itemType);
            if (item1.equals(item)) {
                itemService.unequipItem(hero, item);
            }
        }
        Abilities abilities = item.getAbilities();
        abilities.setStrength(abilities.getStrength() + 1);
        abilities.setDefence(abilities.getDefence() + 1);
        abilities.setIntelligence(abilities.getIntelligence() + 1);
        abilities.setDexterity(abilities.getDexterity() + 1);
        abilities.setAgility(abilities.getAgility() + 1);
        abilities.setSpeed(abilities.getSpeed() + 1);

        hero.getInventory().put(itemType, items);
        BigDecimal currentCoins = hero.getCoins();
        BigDecimal newCoins = currentCoins.subtract(BigDecimal.valueOf(10));
        hero.setCoins(newCoins);
        itemService.itemOperation(hero, item);
        logger.info("Item " + item.getName() + " upgraded successfully.");
        upgradeEquipment(hero);
    }
}
