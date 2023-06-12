package org.gm.location.outside;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.location.Location;
import org.gm.location.city.CityLocation;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OutsideLocation extends Location {
    protected Map<Integer, Runnable> choicesMap = new HashMap<>();
    protected final MonsterFactory monsterFactory = new MonsterFactory();
    protected final FightService fightService = new FightService();
    protected final CharacterMenu characterMenu = new CharacterMenu();
    protected final GameMenu gameMenu = new GameMenu();

    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        logger.info("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
        extracted(scanner);
    }

    public void initializeChoicesMap(Hero hero, CityLocation city) {
        choicesMap.put(1, () -> city.explore(hero));
        choicesMap.put(2, () -> new CastleLocation().explore(hero, city));
        choicesMap.put(3, () -> new HauntedForestLocation().explore(hero, city));
        choicesMap.put(4, () -> new AdventureLocation().explore(hero, city));
        choicesMap.put(5, () -> characterMenu.showCharacterMenu(hero));
        choicesMap.put(6, () -> gameMenu.gameMenu(hero));
    }

    private void handleChoice(int choice) {
        Runnable action = choicesMap.get(choice);
        if (action != null) {
            action.run();
        } else if (choice == 7) {
            logger.info("");
        } else {
            logger.info("Invalid choice. Please try again.");
        }
    }

    void extracted(Scanner scanner) {
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
}
