package org.gm.menu;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.items.*;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.gm.utils.HeroContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.gm.menu.GameMenu.SAVE_FILE_PATH;

@RequiredArgsConstructor
@Component
public class LoadGame {
    private final CityLocation city;
    private final LocationVisitor locationVisitor;
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
                HeroContextHolder.setHero(loadedHero);
                city.explore(locationVisitor);
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
            Map<String, Class<? extends Hero>> heroClasses = new HashMap<>();
            heroClasses.put("Mage", Mage.class);
            heroClasses.put("Knight", Knight.class);
            heroClasses.put("Archer", Archer.class);

            Class<? extends Hero> heroClass = heroClasses.get(typeName);
            if (heroClass != null) {
                return heroClass;
            } else {
                throw new ClassNotFoundException("Unknown hero type: " + typeName);
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
            Map<String, Class<? extends Item>> itemClasses = new HashMap<>();
            itemClasses.put("Chest", Chest.class);
            itemClasses.put("Helmet", Helmet.class);
            itemClasses.put("Necklace", Necklace.class);
            itemClasses.put("Ring", Ring.class);
            itemClasses.put("Shoes", Shoes.class);
            itemClasses.put("Trousers", Trousers.class);
            itemClasses.put("Usable", Usable.class);
            itemClasses.put("Usually", Usually.class);
            itemClasses.put("Weapon", Weapon.class);

            Class<? extends Item> itemClass = itemClasses.get(typeName);
            if (itemClass != null) {
                return itemClass;
            } else {
                throw new ClassNotFoundException("Unknown item type: " + typeName);
            }
        }

    }
}
