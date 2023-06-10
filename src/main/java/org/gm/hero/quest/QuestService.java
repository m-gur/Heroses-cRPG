package org.gm.hero.quest;

import org.gm.hero.entity.Hero;
import org.gm.hero.services.LevelService;

import java.util.List;

public class QuestService {
    LevelService levelService = new LevelService();
    public void isQuestCompleted(Hero hero) {
        List<Quest> quests = hero.getQuests();
        for (Quest quest : quests) {
            boolean allLocationsCompleted = quest.getLocations().values().stream()
                    .allMatch(Boolean::booleanValue);

            if (allLocationsCompleted) {
                quest.setCompleted(true);
                levelService.accumulateExperience(hero, quest.getRewardExperience());
                hero.setCoins(hero.getCoins().add(quest.getRewardCoins()));
            }
        }
    }
}
