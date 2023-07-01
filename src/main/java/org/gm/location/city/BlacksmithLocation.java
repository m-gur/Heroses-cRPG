package org.gm.location.city;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.*;
import org.gm.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BlacksmithLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> exploreOptions = new HashMap<>();
        exploreOptions.put(1, () -> upgradeEquipment(hero));
        exploreOptions.put(2, () -> logger.info("""
            This town is no longer like before..
            Many things have changed, people have changed.
            Now the city is not safe, everyone is scared.
            No one is helping us with the monsters, there were some adventurers.
            Just like you, people paid them to kill monsters and save them.
            After getting the money, they ran away. The problem wasn't solved
            and we lost the money..
            Maybe you want to help us with the monsters?
            If you can provide proof that the monsters have been defeated,
            we will reward you with some coins for your help.
            Think about it, there should be an announcement on the bulletin board.
            """));
        exploreOptions.put(3, () -> super.explore(hero));

        logger.info("""
            Welcome adventurer, my name is Greg.
            What brings you to me?
            Do you want to upgrade your equipment?
            """);
        logger.info("Welcome Greg, my name is " + hero.getName());

        do {
            logger.info("""
                1. Yes, I want to upgrade my equipment
                2. No, I just want to ask you about the town
                3. No, sorry, I got lost. Thanks for your help (leaving)
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Runnable selectedOption = exploreOptions.getOrDefault(choice, () -> logger.info(INVALID));
            selectedOption.run();
        } while (choice != 3);
    }


    private void upgradeEquipment(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> upgradeOptions = new HashMap<>();
        upgradeOptions.put(1, () -> chooseAndUpgradeItem(hero, Helmet.class));
        upgradeOptions.put(2, () -> chooseAndUpgradeItem(hero, Chest.class));
        upgradeOptions.put(3, () -> chooseAndUpgradeItem(hero, Ring.class));
        upgradeOptions.put(4, () -> chooseAndUpgradeItem(hero, Necklace.class));
        upgradeOptions.put(5, () -> chooseAndUpgradeItem(hero, Trousers.class));
        upgradeOptions.put(6, () -> chooseAndUpgradeItem(hero, Shoes.class));
        upgradeOptions.put(7, () -> chooseAndUpgradeItem(hero, Weapon.class));
        upgradeOptions.put(8, () -> explore(hero));

        logger.info("""
            What do you want to upgrade?
            To upgrade an item, you need to pay 10 coins.
            The upgraded item will receive +1 to all stats.
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

            Runnable selectedOption = upgradeOptions.getOrDefault(choice, () -> logger.info(INVALID));
            selectedOption.run();
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
                item.unequipItem(hero);
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
        item.itemOperation(hero);
        logger.info("Item " + item.getName() + " upgraded successfully.");
        upgradeEquipment(hero);
    }
}
