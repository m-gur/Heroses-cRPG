package org.gm.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.gm.hero.entity.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

@Component
public class GameMenu {
    static final String SAVE_FILE_PATH = "src/main/resources/game_save.json";
    private static final Logger logger = LoggerFactory.getLogger(GameMenu.class);

    public void gameMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Map<Integer, Runnable> menuActions = new HashMap<>();
        menuActions.put(1, () -> saveGame(hero));
        menuActions.put(2, () -> exit(0));
        menuActions.put(3, () -> {
        });

        do {
            logger.info("""
                What do you want to do?
                1. Save game
                2. Exit game
                3. Return
                """);

            choice = scanner.nextInt();
            scanner.nextLine();

            Runnable menuAction = menuActions.get(choice);
            if (menuAction != null) {
                menuAction.run();
            } else {
                logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }


    private void saveGame(Hero hero) {
        try (OutputStream os = new FileOutputStream(SAVE_FILE_PATH);
             Writer writer = new OutputStreamWriter(os)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(hero, writer);
            logger.info("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String json = new String(Files.readAllBytes(Paths.get(SAVE_FILE_PATH)));

            json = json.replace("class ", "");

            FileWriter writer = new FileWriter(SAVE_FILE_PATH);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        gameMenu(hero);
    }
}
