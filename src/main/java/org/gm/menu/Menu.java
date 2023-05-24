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

        System.out.println("Welcome to the Heroes!");
        int choice;

        do {
            System.out.println("Please enter the number to use the option:");
            System.out.println("1. Create hero");
            System.out.println("2. Exit the game");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (menuOptions.containsKey(choice)) {
                menuOptions.get(choice).run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    private void createHero() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Which class of hero you want to choose? \n" +
                    "1. Archer \n" +
                    "2. Knight \n" +
                    "3. Mage \n" +
                    "4. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> createHeroNext(new Archer());
                case 2 -> createHeroNext(new Knight());
                case 3 -> createHeroNext(new Mage());
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private Hero createHeroNext(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Hero name: ");
        hero.setName(scanner.nextLine());
        heroService.setDamage(hero);
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));
        heroService.setHP(hero);
        hero.setCurrentHp(hero.getMaxHp());
        abilitiesService.setModifierAbilities(hero);
        abilitiesService.setAbilitiesAfterModifier(hero);
        return hero;
    }
}

