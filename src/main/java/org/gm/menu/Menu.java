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
}

