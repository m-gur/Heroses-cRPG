package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletinBoardLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        boolean firstJourneyQuestIsCompleted = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("First Journey"))
                .allMatch(Quest::isCompleted);
        Map<String, Boolean> locations = new HashMap<>();
        logger.info("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);
        boolean firstJourneyQuest = hero.getQuests().stream()
                .anyMatch(quest -> quest.getName().equals("First Journey"));
        if (!firstJourneyQuest) {
            locations.put("MayorLocation", false);
            locations.put("HauntedForestLocation", false);
            Quest quest = new Quest("First Journey", locations, 100, BigDecimal.valueOf(50), false);
            List<Quest> quests = hero.getQuests();
            quests.add(quest);
            hero.setQuests(quests);
            logger.info("Quest: " + quest + "\n was added to your quests.");
        } else if (firstJourneyQuestIsCompleted) {
            locations.put("CastleLocation", false);
            Quest quest = new Quest("Betrayed", locations, 200, BigDecimal.valueOf(100), false);
            List<Quest> quests = hero.getQuests();
            quests.add(quest);
            hero.setQuests(quests);
            logger.info("Quest: " + quest + "\n was added to your quests.");
        } else {
            logger.info("""
                    Currently, there are no new quests available.
                    Please come back later.
                    """);
        }
    }
}
