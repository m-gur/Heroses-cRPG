package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.location.Location;
import org.gm.location.outside.OutsideLocation;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;

import java.util.Scanner;

public class CityLocation extends Location {
    protected static final String INVALID = "Invalid choice. Please try again.";
    protected static final CharacterMenu characterMenu = new CharacterMenu();
    protected static final GameMenu gameMenu = new GameMenu();
    private static final OutsideLocation outsideLocation = new OutsideLocation();
    @Override
    public void explore(Hero hero) {
        outsideLocation.initializeChoicesMap(hero, this);
        Scanner scanner = new Scanner(System.in);
        int choice;
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
            switch (choice) {
                case 1 -> new BlacksmithLocation().explore(hero);
                case 2 -> new MarketLocation().explore(hero);
                case 3 -> new BulletinBoardLocation().explore(hero);
                case 4 -> outsideLocation.explore(hero);
                case 5 -> characterMenu.showCharacterMenu(hero);
                case 6 -> gameMenu.gameMenu(hero);
                default -> logger.info(INVALID);
            }
        } while (choice != 7);
    }
}
