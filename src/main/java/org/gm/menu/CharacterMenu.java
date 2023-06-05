package org.gm.menu;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.items.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CharacterMenu {
    private final AbilitiesService abilitiesService = new AbilitiesService();
    private final ItemService itemService = new ItemService();
    private static final Logger logger = LoggerFactory.getLogger(CharacterMenu.class);
    private static final String INVALID = "Invalid choice. Please try again.";
    void showCharacterMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            logger.info("""
                    Character menu options:
                    1. Basic stats
                    2. Inventory
                    3. Equip items
                    4. Equipped items
                    5. Distribute skill points
                    6. Reset skill points
                    7. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showHeroBasicStats(hero);
                case 2 -> showHeroInventory(hero);
                case 3 -> equipItems(hero);
                case 4 -> showHeroEquippedItems(hero);
                case 5 -> distributeSkillPoints(hero);
                case 6 -> resetSkillPoints(hero);
                case 7 -> {
                    return;
                }
                default -> logger.info(INVALID);
            }
        } while (choice != 7);
    }

    private void showHeroBasicStats(Hero hero) {
        logger.info( "Name: " + hero.getName() + "\n" +
                            "level: " + hero.getLvl() + "\n" +
                            "experience: " + hero.getExperience() + "\n" +
                            "required experience to level up: " + hero.getRequiredExperience() + "\n" +
                            "current hp: " + hero.getCurrentHp() + "\n" +
                            "max hp: " + hero.getMaxHp() + "\n" +
                            "skill points to distribute: " + hero.getSkillPoints() + "\n" +
                            "damage: " + hero.getDamage() + "\n" +
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
        Map<ItemType, List<Item>> inventory = hero.getInventory();

        if (inventory.isEmpty()) {
            logger.info("Currently you have no items in your inventory.");
            return;
        }

        for (Map.Entry<ItemType, List<Item>> entry : inventory.entrySet()) {
            ItemType itemType = entry.getKey();
            List<Item> items = entry.getValue();

            if (!items.isEmpty()) {
                logger.info(itemType.toString() + " items: " + items);
            }
        }
    }

    private void showHeroEquippedItems(Hero hero) {
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();

        if (equippedItems.isEmpty()) {
            logger.info("Currently you have no equipped items.");
            return;
        }

        for (Map.Entry<ItemType, Item> entry : equippedItems.entrySet()) {
            ItemType itemType = entry.getKey();
            Item item = entry.getValue();
            logger.info(itemType.toString() + " item: " + item);
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
            } else if (choice == 7){
                return;
            } else {
                logger.info(INVALID);
            }
        } while (choice != 7);
        scanner.close();
    }

    private void resetSkillPoints(Hero hero) {
        abilitiesService.resetSkillPoints(hero);
    }

    private void equipItems(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
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

            switch (choice) {
                case 1 -> chooseAndEquipItem(hero, ItemType.HELMET);
                case 2 -> chooseAndEquipItem(hero, ItemType.CHEST);
                case 3 -> chooseAndEquipItem(hero, ItemType.RING);
                case 4 -> chooseAndEquipItem(hero, ItemType.NECKLACE);
                case 5 -> chooseAndEquipItem(hero, ItemType.TROUSERS);
                case 6 -> chooseAndEquipItem(hero, ItemType.SHOES);
                case 7 -> chooseAndEquipItem(hero, ItemType.WEAPON);
                case 8 -> showCharacterMenu(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 8);
    }

    private void chooseAndEquipItem(Hero hero, ItemType itemType) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = hero.getInventory().get(itemType);
        if (items == null || items.isEmpty()) {
            logger.info("No items of this type in inventory.");
            return;
        }

        printItems(items);
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            Item selected = items.get(selectedIndex);
            itemService.itemOperation(hero, selected);
            logger.info("Item equipped: " + selected);
        } else {
            logger.info("Invalid item selection.");
        }
    }

    private void printItems(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            logger.info((i) + ". " + items.get(i));
        }
    }
}
