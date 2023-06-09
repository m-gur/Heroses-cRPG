package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;
import org.gm.location.Location;
import org.gm.location.outside.HauntedForestLocation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinBoardLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        logger.info("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);
        Map<Location, Boolean> locations = new HashMap<>();
        locations.put(new MayorLocation(), false);
        locations.put(new HauntedForestLocation(), false);
        locations.put(new MayorLocation(), false);
        Quest quest = new Quest(locations, 100, BigDecimal.valueOf(50), false);
        List<Quest> quests = hero.getQuests();
        quests.add(quest);
        hero.setQuests(quests);
    }
}
