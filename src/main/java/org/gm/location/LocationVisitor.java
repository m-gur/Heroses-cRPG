package org.gm.location;

import lombok.RequiredArgsConstructor;
import org.gm.location.city.*;
import org.gm.location.outside.AdventureLocation;
import org.gm.location.outside.CastleLocation;
import org.gm.location.outside.HauntedForestLocation;
import org.gm.location.outside.OutsideLocation;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class LocationVisitor {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Map<Integer, Runnable> choicesOutsideMap = new HashMap<>();
    protected Map<Integer, Runnable> choicesCityMap = new HashMap<>();
    protected Map<Integer, Runnable> choicesMarketMap = new HashMap<>();
    protected final CharacterMenu characterMenu;
    protected final GameMenu gameMenu;
    protected final CastleLocation castleLocation;
    protected final CityLocation cityLocation;
    protected final HauntedForestLocation hauntedForestLocation;
    protected final AdventureLocation adventureLocation;
    protected final BlacksmithLocation blacksmithLocation;
    protected final MarketLocation marketLocation;
    protected final BulletinBoardLocation bulletinBoardLocation;
    protected final OutsideLocation outsideLocation;
    protected final HealerLocation healerLocation;
    protected final MayorLocation mayorLocation;
    protected final MerchantLocation merchantLocation;

    public void outsideLocationsChoice(CityLocation city, LocationVisitor locationVisitor) {
        initializeOutsideChoicesMap(city, locationVisitor);
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

            handleChoice(choice, "outside");
        } while (choice != 7);
    }

    public void cityLocationsChoice(CityLocation city, LocationVisitor locationVisitor) {
        initializeCityChoicesMap(city, locationVisitor);
        initializeMarketChoicesMap(locationVisitor);
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

            handleChoice(choice, "city");
        } while (choice != 7);
    }

    public void marketLocationsChoice(LocationVisitor locationVisitor) {
        initializeMarketChoicesMap(locationVisitor);
        Scanner scanner = new Scanner(System.in);
        int choice;

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

            handleChoice(choice, "market");
        } while (choice != 7);
    }

    private void initializeOutsideChoicesMap(CityLocation city, LocationVisitor locationVisitor) {
        choicesOutsideMap.put(1, () -> cityLocation.explore(locationVisitor));
        choicesOutsideMap.put(2, () -> castleLocation.explore(city, locationVisitor));
        choicesOutsideMap.put(3, () -> hauntedForestLocation.explore(city, locationVisitor));
        choicesOutsideMap.put(4, () -> adventureLocation.explore(city, locationVisitor));
        choicesOutsideMap.put(5, characterMenu::showCharacterMenu);
        choicesOutsideMap.put(6, gameMenu::gameMenu);
    }

    private void initializeCityChoicesMap(CityLocation city, LocationVisitor locationVisitor) {
        choicesCityMap.put(1, () -> blacksmithLocation.explore(locationVisitor));
        choicesCityMap.put(2, () -> marketLocation.explore(locationVisitor));
        choicesCityMap.put(3, () -> bulletinBoardLocation.explore(locationVisitor));
        choicesCityMap.put(4, () -> outsideLocation.explore(city, locationVisitor));
        choicesCityMap.put(5, characterMenu::showCharacterMenu);
        choicesCityMap.put(6, gameMenu::gameMenu);
    }

    private void initializeMarketChoicesMap(LocationVisitor locationVisitor) {
        choicesMarketMap.put(1, () -> healerLocation.explore(locationVisitor));
        choicesMarketMap.put(2, () -> mayorLocation.explore(locationVisitor));
        choicesMarketMap.put(3, () -> merchantLocation.explore(locationVisitor));
        choicesMarketMap.put(4, characterMenu::showCharacterMenu);
        choicesMarketMap.put(5, gameMenu::gameMenu);
        choicesMarketMap.put(6, () -> cityLocation.explore(locationVisitor));
    }

    private void handleChoice(int choice, String map) {
        Runnable action = null;
        if (map.equals("market")) {
            action = choicesMarketMap.get(choice);
        } else if (map.equals("city")) {
            action = choicesCityMap.get(choice);
        } else if (map.equals("outside")) {
            action = choicesOutsideMap.get(choice);
        }
        if (action != null) {
            action.run();
        } else if (choice == 7) {
            logger.info("");
        } else {
            logger.info("Invalid choice. Please try again.");
        }
    }
}
