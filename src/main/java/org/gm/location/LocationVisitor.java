package org.gm.location;

import org.gm.hero.entity.Hero;
import org.gm.location.city.CityLocation;
import org.gm.location.outside.AdventureLocation;
import org.gm.location.outside.CastleLocation;
import org.gm.location.outside.HauntedForestLocation;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LocationVisitor {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Map<Integer, Runnable> choicesMap = new HashMap<>();
    protected final CharacterMenu characterMenu = new CharacterMenu();
    protected final GameMenu gameMenu = new GameMenu();

    private void initializeChoicesMap(Hero hero, CityLocation city) {
        choicesMap.put(1, () -> new CityLocation().explore(hero));
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

    public void outsideLocationsChoice(Hero hero, CityLocation city) {
        initializeChoicesMap(hero, city);
        Scanner scanner = new Scanner(System.in);
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
