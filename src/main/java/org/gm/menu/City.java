package org.gm.menu;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.items.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class City {
    private final CharacterMenu characterMenu = new CharacterMenu();
    private final GameMenu gameMenu = new GameMenu();
    private final ItemService itemService = new ItemService();
    private final Outside outside = new Outside();
    private static final Logger logger = LoggerFactory.getLogger(City.class);
    private static final String INVALID = "Invalid choice. Please try again.";

    void exploreCity(Hero hero) {
        outside.initializeChoicesMap(hero, this);
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            logger.info("""
                    Where you want to go?
                    1. Blacksmith
                    2. Market
                    3. Bulletin Board
                    4. Get out of town
                    5. Character menu
                    6. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> blacksmith(hero);
                case 2 -> market(hero);
                case 3 -> bulletinBoard(hero);
                case 4 -> outside.getOutOfTown(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu.gameMenu(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 7);
    }

    private void blacksmith(Hero hero) {
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
                case 3 -> exploreCity(hero);
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
                2. Armor
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
                case 1 -> chooseAndUpgradeItem(hero, ItemType.HELMET);
                case 2 -> chooseAndUpgradeItem(hero, ItemType.CHEST);
                case 3 -> chooseAndUpgradeItem(hero, ItemType.RING);
                case 4 -> chooseAndUpgradeItem(hero, ItemType.NECKLACE);
                case 5 -> chooseAndUpgradeItem(hero, ItemType.TROUSERS);
                case 6 -> chooseAndUpgradeItem(hero, ItemType.SHOES);
                case 7 -> chooseAndUpgradeItem(hero, ItemType.WEAPON);
                case 8 -> blacksmith(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 8);
    }

    private void chooseAndUpgradeItem(Hero hero, ItemType itemType) {
        if (!hero.getCoins().equals(BigDecimal.valueOf(10))) {
            logger.info("Invalid needed coins to upgrade item.");
            upgradeEquipment(hero);
        }
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().get(itemType);
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            upgradeEquipment(hero);
        }

        printItems(items);
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            Item selected = items.get(selectedIndex);
            upgradeItem(hero, selected);
        } else {
            logger.info("Invalid item selection.");
        }
    }

    private void printItems(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            logger.info((i) + ". " + items.get(i));
        }
    }

    private void upgradeItem(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        List<Item> items = hero.getInventory().get(itemType);
        Item item1 = hero.getEquippedItems().get(itemType);
        if (item1.equals(item)) {
            itemService.unequipItem(hero, item);
        }
        Abilities abilities = item.getAbilities();
        abilities.setStrength(abilities.getStrength() + 1);
        abilities.setDefence(abilities.getDefence() + 1);
        abilities.setIntelligence(abilities.getIntelligence() + 1);
        abilities.setDexterity(abilities.getDexterity() + 1);
        abilities.setAgility(abilities.getAgility() + 1);
        abilities.setSpeed(abilities.getSpeed() + 1);

        hero.getInventory().put(itemType, items);
        logger.info("Item " + item.getName() + " upgraded successfully.");
        itemService.itemOperation(hero, item);
        upgradeEquipment(hero);
    }

    private void market(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        logger.info("""
                The center of the town, from this place you can go to mayor,
                sell some staff to merchants or lost your money for other things.
                """);
        do {
            logger.info("""
                    Where you want to go?
                    1. Healer
                    2. Mayor
                    3. Merchant
                    4. Character menu
                    5. Game menu
                    6. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> healer(hero);
                case 2 -> mayor(hero);
                case 3 -> merchant(hero);
                case 4 -> characterMenu.showCharacterMenu(hero);
                case 5 -> gameMenu.gameMenu(hero);
                case 6 -> {
                    return;
                }
                default -> logger.info(INVALID);
            }
        } while (choice != 6);

    }

    private void healer(Hero hero) {
        logger.info("""
                Hello newcomer, your efforts are admired, and in return, all your health points have been restored.
                """);
        hero.setCurrentHp(hero.getMaxHp());
    }

    private void mayor(Hero hero) {
        logger.info("""
                Currently, you cannot meet with the mayor.
                You don't have any business with him, and he also doesn't want to see you at this moment.
                Please come back later.
                """);
    }

    private void merchant(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            logger.info("""
                What item do you want to sell?
                You cannot back after select category.
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
                case 10 -> market(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 10);
    }

    private void chooseAndSellItem(Hero hero, ItemType itemType) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().get(itemType);
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            merchant(hero);
        }

        printItems(items);
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

    private void bulletinBoard(Hero hero) {
        logger.info("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);
    }
}
