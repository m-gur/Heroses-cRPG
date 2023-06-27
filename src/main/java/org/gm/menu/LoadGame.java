package org.gm.menu;

import com.google.gson.*;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.items.entity.*;
import org.gm.location.city.CityLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import static org.gm.menu.GameMenu.SAVE_FILE_PATH;

public class LoadGame {
    private final CityLocation city = new CityLocation();
    private static final Logger logger = LoggerFactory.getLogger(LoadGame.class);
    public void loadGame() {
        try (Reader reader = new FileReader(SAVE_FILE_PATH)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Class.class, new ClassTypeAdapter())
                    .registerTypeAdapter(Item.class, new ItemDeserializer())
                    .registerTypeAdapter(Hero.class, new HeroDeserializer())
                    .create();

            Hero loadedHero = gson.fromJson(reader, Hero.class);
            if (loadedHero != null) {
                logger.info("Game loaded successfully!");
                city.explore(loadedHero);
            } else {
                logger.info("Failed to load game.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Save not found, create new hero.");
        }
    }

    private static class ClassTypeAdapter implements JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return Class.forName(json.getAsString());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
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

    private static class ItemDeserializer implements JsonDeserializer<Item> {
        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String typeName = jsonObject.getAsJsonPrimitive("itemType").getAsString();
            try {
                Class<? extends Item> itemClass = getItemClass(typeName);
                return context.deserialize(jsonObject, itemClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Class<? extends Item> getItemClass(String typeName) throws ClassNotFoundException {
            switch (typeName) {
                case "Chest" -> {
                    return Chest.class;
                }
                case "Helmet" -> {
                    return Helmet.class;
                }
                case "Necklace" -> {
                    return Necklace.class;
                }
                case "Ring" -> {
                    return Ring.class;
                }
                case "Shoes" -> {
                    return Shoes.class;
                }
                case "Trousers" -> {
                    return Trousers.class;
                }
                case "Usable" -> {
                    return Usable.class;
                }
                case "Usually" -> {
                    return Usually.class;
                }
                case "Weapon" -> {
                    return Weapon.class;
                }
                default -> throw new ClassNotFoundException("Unknown item type: " + typeName);
            }
        }
    }
}
