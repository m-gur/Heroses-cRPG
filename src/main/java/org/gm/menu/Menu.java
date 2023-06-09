package org.gm.menu;

import com.google.gson.*;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.services.HeroService;
import org.gm.hero.services.LevelService;
import org.gm.location.city.CityLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;
import static org.gm.menu.GameMenu.SAVE_FILE_PATH;

public class Menu {
    private final HeroService heroService = new HeroService();
    private final AbilitiesService abilitiesService = new AbilitiesService();
    private final LevelService levelService = new LevelService();
    private final CityLocation city = new CityLocation();
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Runnable> menuOptions = new HashMap<>();
        menuOptions.put(1, this::createHero);
        menuOptions.put(2, this::loadGame);

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

    private void loadGame() {
        try (Reader reader = new FileReader(SAVE_FILE_PATH)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Hero.class, new HeroDeserializer())
                    .create();

            Hero loadedHero = gson.fromJson(reader, Hero.class);
            if (loadedHero != null) {
                logger.info("Game loaded successfully!");
                city.explore(loadedHero);
            } else {
                logger.info("Failed to load game.");
                startGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class HeroDeserializer implements JsonDeserializer<Hero> {
        @Override
        public Hero deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String typeName = jsonObject.getAsJsonPrimitive("heroType").getAsString();
            try {
                Class<? extends Hero> heroClass = getHeroClass(typeName);
                return context.deserialize(jsonObject, heroClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Class<? extends Hero> getHeroClass(String typeName) throws ClassNotFoundException {
            switch (typeName) {
                case "Mage" -> {
                    return Mage.class;
                }
                case "Knight" -> {
                    return Knight.class;
                }
                case "Archer" -> {
                    return Archer.class;
                }
                default -> throw new ClassNotFoundException("Unknown hero type: " + typeName);
            }
        }
    }

    private void createHero() {
        Scanner scanner = new Scanner(System.in);
        int choice;
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
            switch (choice) {
                case 1 -> city.explore(createHeroPartTwo(new Archer()));
                case 2 -> city.explore(createHeroPartTwo(new Knight()));
                case 3 -> city.explore(createHeroPartTwo(new Mage()));
                case 4 -> {
                    return;
                }
                default -> logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private Hero createHeroPartTwo(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter your Hero name: ");
        hero.setName(scanner.nextLine());
        heroService.setDamage(hero);
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));
        heroService.setHP(hero);
        hero.setCurrentHp(hero.getMaxHp());
        abilitiesService.setModifierAbilities(hero);
        abilitiesService.setAbilitiesAfterModifier(hero);
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
        return hero;
    }
}
