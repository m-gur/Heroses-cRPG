package org.gm.menu;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.services.LevelService;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.gm.utils.HeroContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import static java.lang.System.exit;

@Component
public class Menu {
    private final AbilitiesService abilitiesService;
    private final LevelService levelService;
    private final CityLocation city;
    private final LoadGame loadGame;
    private final LocationVisitor locationVisitor;
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    @Autowired
    public Menu(AbilitiesService abilitiesService, LevelService levelService, CityLocation city, LoadGame loadGame, LocationVisitor locationVisitor) {
        this.abilitiesService = abilitiesService;
        this.levelService = levelService;
        this.city = city;
        this.loadGame = loadGame;
        this.locationVisitor = locationVisitor;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Runnable> menuOptions = new HashMap<>();
        menuOptions.put(1, this::createHero);
        menuOptions.put(2, loadGame::loadGame);

        logger.info("Welcome to the Heroes!");
        int choice;

        do {
            logger.info("""
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
                logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void createHero() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, Supplier<Hero>> heroMap = new HashMap<>();
        heroMap.put(1, Archer::new);
        heroMap.put(2, Knight::new);
        heroMap.put(3, Mage::new);

        do {
            logger.info("""
                Which class of hero you want to choose?
                1. Archer
                2. Knight
                3. Mage
                4. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            if (heroMap.containsKey(choice)) {
                createHeroPartTwo(heroMap.get(choice).get());
                city.explore(locationVisitor);
            } else if (choice == 4) {
                return;
            } else {
                logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }


    private void createHeroPartTwo(Hero hero) {
        HeroContextHolder.setHero(hero);
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter your Hero name: ");
        hero.setName(scanner.nextLine());
        hero.setDamage();
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));
        hero.setMaxHp(hero.setHP());
        abilitiesService.setModifierAbilities();
        abilitiesService.setAbilitiesAfterModifier();
        hero.setCurrentHp(hero.getMaxHp());
        hero.setHeroType(hero.getHeroType());
        hero.setCoins(BigDecimal.ZERO);
        logger.info("Welcome " + hero.getName() + "!");
        logger.info("""
                I am really glad to see you want to spent a few great moments in our world.
                However you must to know this city has a problem with monsters..
                There are some types of them, some of them are familiar to city dwellers.
                They can help you to describe and got needed staff to explore the world.
                Unfortunately there are a few monsters, which are really dangerous..
                Their goal is to destroy this world, getting armies and start the war with humans!
                You must help this people survive, find the solution...
                 """);
    }
}
