package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;

import java.util.List;

public class MayorLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        boolean firstJourneyQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("First Journey"))
                .allMatch(quest -> !quest.isCompleted());
        if (firstJourneyQuest) {
            logger.info("""
                    Hello traveler, I see that you're interested in helping us.
                    I'm very glad, and so are the residents.
                    You must head to the haunted forest, as that's where the beast lurks, attacking the townsfolk.
                    Please slay it, and you will receive a reward!
                    """);
            Quest firstJourney = hero.getQuests().stream()
                    .filter(quest -> quest.getName().equals("First Journey")).findFirst().orElseThrow();
            firstJourney.getLocations().replace("MayorLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("First Journey"));
            quests.add(firstJourney);
            hero.setQuests(quests);
        } else {
            logger.info("""
                    Currently, you cannot meet with the mayor.
                    You don't have any business with him, and he also doesn't want to see you at this moment.
                    Please come back later.
                    """);
        }
    }
}
