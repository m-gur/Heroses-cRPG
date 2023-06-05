package org.gm.menu;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.monster.entity.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Outside {
    private final Map<Integer, Runnable> choicesMap = new HashMap<>();
    private final MonsterFactory monsterFactory = new MonsterFactory();
    private final FightService fightService = new FightService();
    private final CharacterMenu characterMenu = new CharacterMenu();
    private final GameMenu gameMenu = new GameMenu();
    private static final Logger logger = LoggerFactory.getLogger(Outside.class);

    void initializeChoicesMap(Hero hero, City city) {
        choicesMap.put(1, () -> city.exploreCity(hero));
        choicesMap.put(2, () -> castle(hero));
        choicesMap.put(3, () -> hauntedForest(hero));
        choicesMap.put(4, () -> adventure(hero, city));
        choicesMap.put(5, () -> characterMenu.showCharacterMenu(hero));
        choicesMap.put(6, () -> gameMenu.gameMenu(hero));
    }

    private void handleChoice(int choice ) {
        Runnable action = choicesMap.get(choice);
        if (action != null) {
            action.run();
        } else if (choice == 7) {
            logger.info("");
        } else {
            logger.info("Invalid choice. Please try again.");
        }
    }
    private void extracted(Scanner scanner) {
        int choice;
        do {
            logger.info("""
                            1. City
                            2. Castle
                            3. Haunted Forest
                            4. Adventure
                            5. Character Menu
                            6. Game Menu
                            """);
            choice = scanner.nextInt();
            scanner.nextLine();

            handleChoice(choice);
        } while (choice != 7);
    }
    void getOutOfTown(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        logger.info("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
        extracted(scanner);
    }

    private void hauntedForest(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        logger.info("""
        Currently, you are too weak for this place. Come back when you are stronger.
        Where would you like to go?
        """);

        extracted(scanner);
    }

    private void castle(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        logger.info("""
        You have arrived at the castle, but for now, you can't do anything more here.
        Where would you like to go?
        """);

        extracted(scanner);
    }

    private void adventure(Hero hero, City city) {
        Monster randomMonster = monsterFactory.createRandomMonster(hero);
        Scanner scanner = new Scanner(System.in);
        logger.info("""
                You are brave, choosing an adventure.
                Are you able to find something more than just monsters here?
                """);
        fightService.performBattle(hero, randomMonster);
        if (hero.getCurrentHp() <= 0) {
            logger.info("""
                    You have died and have been transported to the main location in the game.
                    Your health has been restored.
                    """);
            hero.setCurrentHp(hero.getMaxHp());
            city.exploreCity(hero);
        }
        extracted(scanner);
    }
}
