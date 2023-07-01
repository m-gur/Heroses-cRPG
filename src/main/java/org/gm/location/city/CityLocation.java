package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.location.Location;
import org.gm.location.outside.OutsideLocation;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CityLocation extends Location {
    protected static final String INVALID = "Invalid choice. Please try again.";
    protected static final CharacterMenu characterMenu = new CharacterMenu();
    protected static final GameMenu gameMenu = new GameMenu();
    private static final OutsideLocation outsideLocation = new OutsideLocation();
    @Override
    public void explore(Hero hero) {
        logger.info("""
                You are in the city center, from this place everything begins.
                """);
        cityLocationsChoice(hero);
    }

    private void cityLocationsChoice(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Map<Integer, Runnable> locationChoices = new HashMap<>();
        locationChoices.put(1, () -> new BlacksmithLocation().explore(hero));
        locationChoices.put(2, () -> new MarketLocation().explore(hero));
        locationChoices.put(3, () -> new BulletinBoardLocation().explore(hero));
        locationChoices.put(4, () -> outsideLocation.explore(hero, this));
        locationChoices.put(5, () -> characterMenu.showCharacterMenu(hero));
        locationChoices.put(6, () -> gameMenu.gameMenu(hero));

        do {
            logger.info("""
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

            Runnable selectedOption = locationChoices.getOrDefault(choice, () -> logger.info(INVALID));
            selectedOption.run();
        } while (choice != 7);
    }

}
