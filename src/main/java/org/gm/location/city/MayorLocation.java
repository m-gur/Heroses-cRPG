package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;
import org.gm.location.LocationVisitor;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.gm.utils.HeroContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MayorLocation extends CityLocation {

    public MayorLocation(CharacterMenu characterMenu, GameMenu gameMenu) {
        super(characterMenu, gameMenu);
    }

    @Override
    public void explore(LocationVisitor locationVisitor) {
        Hero hero = HeroContextHolder.getHero();
        boolean firstJourneyQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("First Journey"))
                .allMatch(quest -> !quest.isCompleted());
        boolean endGameQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame"))
                .allMatch(quest -> !quest.isCompleted());
        boolean endGameQuestLocation = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame"))
                .filter(quest -> quest.getLocations().containsKey("HauntedForestLocation"))
                .map(quest -> quest.getLocations().get("HauntedForestLocation"))
                .anyMatch(Boolean::booleanValue);
        if (hero.getQuests().isEmpty()) {
            logger.info("""
                    Currently, you cannot meet with the mayor.
                    You don't have any business with him, and he also doesn't want to see you at this moment.
                    Please come back later.
                    """);
        } else if (firstJourneyQuest) {
            firstJourneyQuest();
        } else if (endGameQuest && endGameQuestLocation) {
            endGameQuest();
        }
    }

    private void firstJourneyQuest() {
        Hero hero = HeroContextHolder.getHero();
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
        firstJourney.isQuestCompleted();
    }

    private void endGameQuest() {
        Hero hero = HeroContextHolder.getHero();
        logger.info("""
                Thank you, the city is immensely grateful for your contributions.
                We will never forget what you have done for us.
                Thanks to you, we have regained peace, and we can finally live normally.
                You can stay with us if you want, or you can continue on your adventure!
                Regardless of what you choose, we thank you!
                """);
        Quest endGame = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame")).findFirst().orElseThrow();
        endGame.getLocations().replace("MayorLocation", false, true);
        List<Quest> quests = hero.getQuests();
        quests.removeIf(quest -> quest.getName().equals("EndGame"));
        quests.add(endGame);
        hero.setQuests(quests);
        endGame.isQuestCompleted();
    }
}
