package org.gm.location.city;

import org.gm.hero.entity.Hero;

import java.util.Scanner;

public class MarketLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        logger.info("""
                The center of the town, from this place you can go to mayor,
                sell some staff to merchants or lost your money for other things.
                """);
        do {
            logger.info("""
                    Where you want to go?
                    1. Healer
                    2. Mayor
                    3. Merchant
                    4. Character menu
                    5. Game menu
                    6. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> new HealerLocation().explore(hero);
                case 2 -> new MayorLocation().explore(hero);
                case 3 -> new MerchantLocation().explore(hero);
                case 4 -> characterMenu.showCharacterMenu(hero);
                case 5 -> gameMenu.gameMenu(hero);
                case 6 -> {
                    return;
                }
                default -> logger.info(INVALID);
            }
        } while (choice != 6);
    }
}
