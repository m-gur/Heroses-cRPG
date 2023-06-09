package org.gm.menu;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.*;
import org.gm.hero.quest.Quest;
import org.gm.utils.HeroContextHolder;
import org.gm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CharacterMenu {
    private final AbilitiesService abilitiesService = new AbilitiesService();
    private static final Logger logger = LoggerFactory.getLogger(CharacterMenu.class);
    private static final String INVALID = "Invalid choice. Please try again.";

    public void showCharacterMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> menuOptions = new HashMap<>();
        menuOptions.put(1, this::showHeroBasicStats);
        menuOptions.put(2, this::showHeroInventory);
        menuOptions.put(3, this::equipItems);
        menuOptions.put(4, this::showHeroEquippedItems);
        menuOptions.put(5, this::distributeSkillPoints);
        menuOptions.put(6, this::resetSkillPoints);
        menuOptions.put(7, this::showQuests);
        menuOptions.put(8, this::restoreHp);
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

    private void showHeroBasicStats() {
        Hero hero = HeroContextHolder.getHero();
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

    private void showHeroInventory() {
        Hero hero = HeroContextHolder.getHero();
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

    private void showHeroEquippedItems() {
        Hero hero = HeroContextHolder.getHero();
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

    private void distributeSkillPoints() {
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
                abilitiesService.distributeSkillPoints(skillPointsDistribution);
            } else if (choice == 7) {
                return;
            } else {
                logger.info(INVALID);
            }
        } while (choice != 7);
        scanner.close();
    }

    private void resetSkillPoints() {
        Hero hero = HeroContextHolder.getHero();
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
                showCharacterMenu();
            }
            abilitiesService.resetSkillPoints();
            BigDecimal currentCoins = hero.getCoins();
            BigDecimal newCoins = currentCoins.subtract(BigDecimal.valueOf(cost));
            hero.setCoins(newCoins);
            logger.info("Points reset successfully");
        } else {
            showCharacterMenu();
        }
    }

    private void equipItems() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Runnable> equipOptions = new HashMap<>();
        equipOptions.put(1, () -> chooseAndEquipItem(Helmet.class));
        equipOptions.put(2, () -> chooseAndEquipItem(Chest.class));
        equipOptions.put(3, () -> chooseAndEquipItem(Ring.class));
        equipOptions.put(4, () -> chooseAndEquipItem(Necklace.class));
        equipOptions.put(5, () -> chooseAndEquipItem(Trousers.class));
        equipOptions.put(6, () -> chooseAndEquipItem(Shoes.class));
        equipOptions.put(7, () -> chooseAndEquipItem(Weapon.class));
        equipOptions.put(8, this::showCharacterMenu);

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


    private void chooseAndEquipItem(Class<? extends Item> itemType) {
        operationOnItems(itemType, "Equip");
    }

    private void showQuests() {
        Hero hero = HeroContextHolder.getHero();
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

    private void restoreHp() {
        operationOnItems(Usable.class, "Restore");
    }

    private void operationOnItems(Class<? extends Item> itemType, String operation) {
        Hero hero = HeroContextHolder.getHero();
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
                hero.restoreHP(selected.getName());
            } else if (operation.equals("Equip")) {
                Item selected = items.get(selectedIndex);
                selected.itemOperation();
                logger.info("Item equipped: " + selected);
            } else {
                logger.error("Bad operation request.");
            }
        } else {
            logger.info("Invalid item selection.");
        }
    }
}
