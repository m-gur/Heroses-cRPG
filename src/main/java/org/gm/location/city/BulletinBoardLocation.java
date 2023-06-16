package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BulletinBoardLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        boolean firstJourneyQuestIsCompleted = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("First Journey"))
                .allMatch(Quest::isCompleted);
        boolean betrayedQuestIsCompleted = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("Betrayed"))
                .allMatch(Quest::isCompleted);
        Map<String, Boolean> locations = new LinkedHashMap<>();
        logger.info("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);
        boolean firstJourneyQuest = hero.getQuests().stream()
                .anyMatch(quest -> quest.getName().equals("First Journey"));
        boolean betrayedQuest = hero.getQuests().stream()
                .anyMatch(quest -> quest.getName().equals("Betrayed"));
        if (!firstJourneyQuest) {
            locations.put("MayorLocation", false);
            locations.put("HauntedForestLocation", false);
            Quest quest = new Quest("First Journey", locations, 100, BigDecimal.valueOf(50));
            addQuest(hero, quest);
        } else if (firstJourneyQuestIsCompleted && !betrayedQuest) {
            locations.put("CastleLocation", false);
            Quest quest = new Quest("Betrayed", locations, 200, BigDecimal.valueOf(100));
            addQuest(hero, quest);
        } else if (betrayedQuestIsCompleted) {
            locations.put("CastleLocation", false);
            locations.put("HauntedForestLocation", false);
            locations.put("MayorLocation", false);
            Quest quest = new Quest("EndGame", locations, 1000, BigDecimal.valueOf(500));
            addQuest(hero, quest);
        } else {
            logger.info("""
                    Currently, there are no new quests available.
                    Please come back later.
                    """);
        }
    }

    private void addQuest(Hero hero, Quest quest) {
        List<Quest> quests = hero.getQuests();
        quests.add(quest);
        hero.setQuests(quests);
        logger.info("Quest: " + quest + "\n was added to your quests.");
    }
}
