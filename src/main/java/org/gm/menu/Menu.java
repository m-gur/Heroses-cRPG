package org.gm.menu;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.services.HeroService;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;

import java.util.*;

import static java.lang.System.exit;

public class Menu {
    private HeroService heroService = new HeroService();
    private AbilitiesService abilitiesService = new AbilitiesService();
    private LevelService levelService = new LevelService();
    private FightService fightService = new FightService();
    private MonsterFactory monsterFactory = new MonsterFactory();
    private CharacterMenu characterMenu = new CharacterMenu();

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Runnable> menuOptions = new HashMap<>();
        menuOptions.put(1, this::createHero);
        menuOptions.put(2, this::loadGame);

        System.out.println("Welcome to the Heroes!");
        int choice;

        do {
            System.out.println("""
                    Please enter the number to use the option:
                    1. Create new hero
                    2. Load game
                    3. Exit the game""");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (menuOptions.containsKey(choice)) {
                menuOptions.get(choice).run();
            } else if (choice == 3) {
                exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    //TODO create logic to loading game
    private void loadGame() {

    }

    private void createHero() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                    Which class of hero you want to choose?
                    1. Archer
                    2. Knight
                    3. Mage
                    4. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> exploreCity(createHeroPartTwo(new Archer()));
                case 2 -> exploreCity(createHeroPartTwo(new Knight()));
                case 3 -> exploreCity(createHeroPartTwo(new Mage()));
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private Hero createHeroPartTwo(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Hero name: ");
        hero.setName(scanner.nextLine());
        heroService.setDamage(hero);
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));
        heroService.setHP(hero);
        hero.setCurrentHp(hero.getMaxHp());
        abilitiesService.setModifierAbilities(hero);
        abilitiesService.setAbilitiesAfterModifier(hero);

        System.out.println("Welcome " + hero.getName() + "! \n" +
                "I am really glad to see you want to spent a few great moments in our world.\n" +
                "However you must to know this city has a problem with monsters..\n" +
                "There are some types of them, some of them are familiar to city dwellers.\n" +
                "They can help you to describe and got needed staff to explore the world.\n" +
                "Unfortunately there are a few monsters, which are really dangerous..\n" +
                "Their goal is to destroy this world, getting armies and start the war with humans!\n" +
                "You must help this people survive, find the solution...");

        return hero;
    }

    private void exploreCity(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
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
                case 4 -> getOutOfTown(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void blacksmith(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
    Welcome adventurer, my name is Greg.
    What brings you to me?
    Are you want to upgrade your equipment?
    """);
        System.out.println("Welcome Greg, my name is " + hero.getName());
        do {
            System.out.println("""
                    1. Yes, I want to upgrade equipment
                    2. No, only want to ask you about the town
                    3. No, sorry I got lost, thanks (leaving)
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> upgradeEquipment(hero);
                case 2 -> System.out.println("""
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
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void upgradeEquipment(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String itemName;
        System.out.println("""
                What you want to upgrade?
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
                case 1 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.HELMET, itemName);
                }
                case 2 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.CHEST, itemName);
                }
                case 3 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.RING, itemName);
                }
                case 4 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.NECKLACE, itemName);
                }
                case 5 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.TROUSERS, itemName);
                }
                case 6 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.SHOES, itemName);
                }
                case 7 -> {
                    itemName = getItemName(scanner);
                    upgradeItem(hero, ItemType.WEAPON, itemName);
                }
                case 8 -> blacksmith(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static String getItemName(Scanner scanner) {
        String itemName;
        System.out.println("Enter item name to upgrade: ");
        itemName = scanner.nextLine();
        return itemName;
    }

    private void upgradeItem(Hero hero, ItemType itemType, String itemName) {
        List<Item> items = hero.getInventory().get(itemType);
        if (items == null) {
            System.out.println("Currently you haven't items of type: " + itemType);
            upgradeEquipment(hero);
        } else {
            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    Abilities abilities = item.getAbilities();
                    abilities.setStrength(abilities.getStrength() + 1);
                    abilities.setDefence(abilities.getDefence() + 1);
                    abilities.setIntelligence(abilities.getIntelligence() + 1);
                    abilities.setDexterity(abilities.getDexterity() + 1);
                    abilities.setAgility(abilities.getAgility() + 1);
                    abilities.setSpeed(abilities.getSpeed() + 1);

                    hero.getInventory().put(itemType, items);
                    System.out.println("Item " + itemName + " upgraded successfully.");
                    abilitiesService.setAbilitiesAfterModifier(hero);
                    upgradeEquipment(hero);
                }
            }
        }
        System.out.println("Item of given name '" + itemName + "' does not exist in your equipment.");
        upgradeEquipment(hero);
    }


    private void market(Hero hero) {
        System.out.println("""
                The center of the town, from this place you can go to mayor,
                sell some staff to merchants or lost your money for other things.
                """);
    }

    private void bulletinBoard(Hero hero) {
        System.out.println("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);

    }

    private void getOutOfTown(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
        do {
            System.out.println("""
                    1. Forgotten Field
                    2. Back to the town
                    3. Haunted Forest
                    4. Adventure
                    5. Character menu
                    6. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> forgottenField(hero);
                case 2 -> exploreCity(hero);
                case 3 -> hauntedForest(hero);
                case 4 -> adventure(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void hauntedForest(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                Currently, you are too weak for this place. Come back when you are stronger.
                Where would you like to go?
                """);
        do {
            System.out.println("""
                    1. Forgotten Field
                    2. Castle
                    3. Back to the town
                    4. Adventure
                    5. Character menu
                    6. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> forgottenField(hero);
                case 2 -> castle(hero);
                case 3 -> exploreCity(hero);
                case 4 -> adventure(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void castle(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                You have arrived at the castle, but for now, you can't do anything more here.
                Where would you like to go?
                """);
        do {
            System.out.println("""
                    1. Forgotten Field
                    2. Back to the town
                    3. Haunted Forest
                    4. Adventure
                    5. Character menu
                    6. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> forgottenField(hero);
                case 2 -> exploreCity(hero);
                case 3 -> hauntedForest(hero);
                case 4 -> adventure(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void forgottenField(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                You hear strange voices in the vicinity, as if they were crows.
                In the distance, you see an old scarecrow, and it seems to be moving.
                To the west, amidst long-withered crops, there is a house.
                Do you want to continue further? Or would you prefer to go somewhere else?
                """);
        do {
            System.out.println("""
                    1. Back to the town
                    2. Explore the house
                    3. Castle
                    4. Haunted Forest
                    5. Adventure
                    6. Character menu
                    7. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> exploreCity(hero);
                case 2 -> exploreHouse(hero);
                case 3 -> castle(hero);
                case 4 -> hauntedForest(hero);
                case 5 -> adventure(hero);
                case 6 -> characterMenu.showCharacterMenu(hero);
                case 7 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void exploreHouse(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                As you approach the old house, a strange smell lingers in the air, accompanied by an weird aura.
                You ponder whether you truly want to stay in this place. Are you sure you want to be here?
                What would you like to do?
                """);
        do {
            System.out.println("""
                    1. Back to the town
                    2. Forgotten Field
                    3. Castle
                    4. Haunted Forest
                    5. Adventure
                    6. Character menu
                    7. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> exploreCity(hero);
                case 2 -> forgottenField(hero);
                case 3 -> castle(hero);
                case 4 -> hauntedForest(hero);
                case 5 -> adventure(hero);
                case 6 -> characterMenu.showCharacterMenu(hero);
                case 7 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void adventure(Hero hero) {
        Monster randomMonster = monsterFactory.createRandomMonster(hero);
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("""
                You are brave, choosing an adventure.
                Are you able to find something more than just monsters here?
                """);
        fightService.performBattle(hero, randomMonster);
        if (hero.getCurrentHp() <= 0) {
            System.out.println("""
                    You have died and have been transported to the main location in the game.
                    Your health has been restored.
                    """);
            hero.setCurrentHp(hero.getMaxHp());
            exploreCity(hero);
        }
        do {
            System.out.println("""
                    1. Back to the town
                    2. Adventure
                    3. Character menu
                    4. Game menu
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> exploreCity(hero);
                case 2 -> adventure(hero);
                case 3 -> characterMenu.showCharacterMenu(hero);
                case 4 -> gameMenu(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void gameMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                    What you want to do?
                    1. Save game
                    2. Exit game
                    3. Return to the game in Town
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> saveGame(hero);
                case 2 -> exit(0);
                case 3 -> exploreCity(hero);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    //TODO creating logic to saving game
    private void saveGame(Hero hero) {

    }
}
