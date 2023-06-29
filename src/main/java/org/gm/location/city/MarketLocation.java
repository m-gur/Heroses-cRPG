package org.gm.location.city;

import org.gm.hero.entity.Hero;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MarketLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        logger.info("""
            The center of the town, from this place you can go to the mayor,
            sell some stuff to merchants, or lose your money for other things.
            """);
        marketLocationsChoice(hero);
    }

    private void marketLocationsChoice(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Map<Integer, Runnable> locationActions = new HashMap<>();
        locationActions.put(1, () -> new HealerLocation().explore(hero));
        locationActions.put(2, () -> new MayorLocation().explore(hero));
        locationActions.put(3, () -> new MerchantLocation().explore(hero));
        locationActions.put(4, () -> characterMenu.showCharacterMenu(hero));
        locationActions.put(5, () -> gameMenu.gameMenu(hero));
        locationActions.put(6, () -> {
        });

        do {
            logger.info("""
                Where do you want to go?
                1. Healer
                2. Mayor
                3. Merchant
                4. Character menu
                5. Game menu
                6. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Runnable locationAction = locationActions.get(choice);
            if (locationAction != null) {
                locationAction.run();
            } else {
                logger.info(INVALID);
            }
        } while (choice != 6);
    }

}
