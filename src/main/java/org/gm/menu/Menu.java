package org.gm.menu;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.services.HeroService;
import org.gm.hero.services.LevelService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {
    private HeroService heroService = new HeroService();
    private AbilitiesService abilitiesService = new AbilitiesService();
    private LevelService levelService = new LevelService();

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
                case 5 -> showCharacterMenu(hero);
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
        System.out.println("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
    }

    private void showCharacterMenu(Hero hero) {

    }
    private void gameMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                    What you want to do?
                    1. Save game
                    2. Exit game
                    3. Return to the game
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
