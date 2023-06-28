package org.gm.menu;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.*;
import org.gm.hero.items.services.ItemService;
import org.gm.hero.quest.Quest;
import org.gm.hero.services.HeroService;
import org.gm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CharacterMenu {
    private final AbilitiesService abilitiesService = new AbilitiesService();
    private final ItemService itemService = new ItemService();
    private final HeroService heroService = new HeroService();
    private static final Logger logger = LoggerFactory.getLogger(CharacterMenu.class);
    private static final String INVALID = "Invalid choice. Please try again.";

    public void showCharacterMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> menuOptions = new HashMap<>();
        menuOptions.put(1, () -> showHeroBasicStats(hero));
        menuOptions.put(2, () -> showHeroInventory(hero));
        menuOptions.put(3, () -> equipItems(hero));
        menuOptions.put(4, () -> showHeroEquippedItems(hero));
        menuOptions.put(5, () -> distributeSkillPoints(hero));
        menuOptions.put(6, () -> resetSkillPoints(hero));
        menuOptions.put(7, () -> showQuests(hero));
        menuOptions.put(8, () -> restoreHp(hero));
        menuOptions.put(9, () -> {});

        do {
            logger.info("""
                Character menu options:
                1. Basic stats
                2. Inventory
                3. Equip items
                4. Equipped items
                5. Distribute skill points
                6. Reset skill points
                7. Quests
                8. Restore HP
                9. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Runnable selectedOption = menuOptions.getOrDefault(choice, () -> logger.info(INVALID));
            selectedOption.run();
        } while (choice != 9);
    }

    private void showHeroBasicStats(Hero hero) {
        logger.info("Name: " + hero.getName() + "\n" +
                    "class: " + hero.getHeroType() + "\n" +
                    "level: " + hero.getLvl() + "\n" +
                    "experience: " + hero.getExperience() + "\n" +
                    "required experience to level up: " + hero.getRequiredExperience() + "\n" +
                    "current hp: " + hero.getCurrentHp() + "\n" +
                    "max hp: " + hero.getMaxHp() + "\n" +
                    "skill points to distribute: " + hero.getSkillPoints() + "\n" +
                    "damage: " + hero.getDamage() + "\n" +
                    "critical change: " + hero.getCriticalChance() + "\n" +
                    "coins: " + hero.getCoins() + "\n\n" +
                    "Abilities: \n" +
                    "strength: " + hero.getAbilitiesAfterModifier().getStrength() + "\n" +
                    "defence: " + hero.getAbilitiesAfterModifier().getDefence() + "\n" +
                    "intelligence: " + hero.getAbilitiesAfterModifier().getIntelligence() + "\n" +
                    "dexterity: " + hero.getAbilitiesAfterModifier().getDexterity() + "\n" +
                    "agility: " + hero.getAbilitiesAfterModifier().getAgility() + "\n" +
                    "speed: " + hero.getAbilitiesAfterModifier().getSpeed() + "\n"
        );
    }

    private void showHeroInventory(Hero hero) {
        Map<Class<? extends Item>, List<Item>> inventory = hero.getInventory();

        if (inventory.isEmpty()) {
            logger.info("Currently you have no items in your inventory.");
            return;
        }

        for (Map.Entry<Class<? extends Item>, List<Item>> entry : inventory.entrySet()) {
            Class<? extends Item> itemType = entry.getKey();
            List<Item> items = entry.getValue();

            if (!items.isEmpty()) {
                logger.info(itemType.getSimpleName() + " items: " + items);
            }
        }
    }

    private void showHeroEquippedItems(Hero hero) {
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();

        if (equippedItems.isEmpty()) {
            logger.info("Currently you have no equipped items.");
            return;
        }

        for (Map.Entry<Class<? extends Item>, Item> entry : equippedItems.entrySet()) {
            Class<? extends Item> itemType = entry.getKey();
            Item item = entry.getValue();
            logger.info(itemType.getSimpleName() + " item: " + item);
        }
    }

    private void distributeSkillPoints(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        String pointsQuestion = "How many points do you want to add?";

        Map<Integer, String> skillOptions = new HashMap<>();
        skillOptions.put(1, "strength");
        skillOptions.put(2, "defence");
        skillOptions.put(3, "intelligence");
        skillOptions.put(4, "dexterity");
        skillOptions.put(5, "agility");
        skillOptions.put(6, "speed");

        int choice;
        do {
            logger.info("""
                    Which skill do you want to add points to?
                    1. Strength
                    2. Defence
                    3. Intelligence
                    4. Dexterity
                    5. Agility
                    6. Speed
                    7. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();

            if (skillOptions.containsKey(choice)) {
                logger.info(pointsQuestion);
                int skillPoints = scanner.nextInt();
                scanner.nextLine();
                String skillName = skillOptions.get(choice);
                Map<String, Integer> skillPointsDistribution = new HashMap<>();
                skillPointsDistribution.put(skillName, skillPoints);
                abilitiesService.distributeSkillPoints(hero, skillPointsDistribution);
            } else if (choice == 7) {
                return;
            } else {
                logger.info(INVALID);
            }
        } while (choice != 7);
        scanner.close();
    }

    private void resetSkillPoints(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int cost = 10 * hero.getLvl();
        int choice;
        logger.info("Reset cost: " + cost + "\n" +
                           "Are you agreed to reset?\n" +
                           "1. Yes\n" +
                           "2. No\n");
        choice = scanner.nextInt();
        if (choice == 1) {
            if (hero.getCoins().compareTo(BigDecimal.valueOf(cost)) < 0) {
                logger.info("Invalid needed coins to upgrade item.");
                showCharacterMenu(hero);
            }
            abilitiesService.resetSkillPoints(hero);
            BigDecimal currentCoins = hero.getCoins();
            BigDecimal newCoins = currentCoins.subtract(BigDecimal.valueOf(cost));
            hero.setCoins(newCoins);
            logger.info("Points reset successfully");
        } else {
            showCharacterMenu(hero);
        }
    }

    private void equipItems(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> equipOptions = new HashMap<>();
        equipOptions.put(1, () -> chooseAndEquipItem(hero, Helmet.class));
        equipOptions.put(2, () -> chooseAndEquipItem(hero, Chest.class));
        equipOptions.put(3, () -> chooseAndEquipItem(hero, Ring.class));
        equipOptions.put(4, () -> chooseAndEquipItem(hero, Necklace.class));
        equipOptions.put(5, () -> chooseAndEquipItem(hero, Trousers.class));
        equipOptions.put(6, () -> chooseAndEquipItem(hero, Shoes.class));
        equipOptions.put(7, () -> chooseAndEquipItem(hero, Weapon.class));
        equipOptions.put(8, () -> showCharacterMenu(hero));

        do {
            logger.info("""
                What item do you want to equip?
                1. Helmet
                2. Chest
                3. Ring
                4. Necklace
                5. Trousers
                6. Shoes
                7. Weapon
                8. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Runnable selectedOption = equipOptions.getOrDefault(choice, () -> logger.info(INVALID));
            selectedOption.run();
        } while (choice != 8);
    }


    private void chooseAndEquipItem(Hero hero, Class<? extends Item> itemType) {
        operationOnItems(hero, itemType, "Equip");
    }

    private void showQuests(Hero hero) {
        boolean anyCompletedQuest = hero.getQuests().stream()
                .filter(Quest::isCompleted)
                .anyMatch(quest -> true);
        boolean anyUnCompletedQuest = hero.getQuests().stream()
                .filter(Quest::isCompleted)
                .anyMatch(quest -> false);
        List<Quest> completedQuests = hero.getQuests().stream()
                .filter(Quest::isCompleted)
                .collect(Collectors.toList());
        List<Quest> notCompletedQuests = hero.getQuests().stream()
                .filter(quest -> !quest.isCompleted())
                .collect(Collectors.toList());
        if (!anyUnCompletedQuest) {
            logger.info("Actual quests: ");
            for (Quest quest : notCompletedQuests) {
                logger.info(quest.toString());
            }
        }
        if (anyCompletedQuest) {
            logger.info("Completed quests: ");
            for (Quest quest : completedQuests) {
                logger.info(quest.toString());
            }
        }
    }

    private void restoreHp(Hero hero) {
        operationOnItems(hero, Usable.class, "Restore");
    }

    private void operationOnItems(Hero hero, Class<? extends Item> itemType, String operation) {
        Scanner scanner = new Scanner(System.in);
            List<Item> items = hero.getInventory().get(itemType);
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            return;
        }
        Utils.printItems(items);
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            if (operation.equals("Restore")) {
                Item selected = items.get(selectedIndex);
                heroService.restoreHP(hero, selected.getName());
            } else if (operation.equals("Equip")) {
                Item selected = items.get(selectedIndex);
                itemService.itemOperation(hero, selected);
                logger.info("Item equipped: " + selected);
            } else {
                logger.error("Bad operation request.");
            }
        } else {
            logger.info("Invalid item selection.");
        }
    }
}
