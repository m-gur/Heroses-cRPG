package org.gm.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.gm.hero.entity.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class GameMenu {
    static final String SAVE_FILE_PATH = "src/main/resources/game_save.json";
    private static final Logger logger = LoggerFactory.getLogger(GameMenu.class);

    public void gameMenu(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            logger.info("""
                    What you want to do?
                    1. Save game
                    2. Exit game
                    3. Return
                    """);

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> saveGame(hero);
                case 2 -> exit(0);
                case 3 -> {
                    return;
                }
                default -> logger.info("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void saveGame(Hero hero) {
        try (OutputStream os = new FileOutputStream(SAVE_FILE_PATH);
             Writer writer = new OutputStreamWriter(os)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(hero, writer);
            logger.info("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameMenu(hero);
    }
}
