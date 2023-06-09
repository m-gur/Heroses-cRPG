package org.gm.location.outside;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.gm.monster.Elite;
import org.gm.monster.Monster;
import org.gm.utils.HeroContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CastleLocation extends OutsideLocation {

    public CastleLocation(MonsterFactory monsterFactory, FightService fightService) {
        super(monsterFactory, fightService);
    }

    public void explore(CityLocation city, LocationVisitor locationVisitor) {
        Hero hero = HeroContextHolder.getHero();
        boolean betrayedQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("Betrayed"))
                .allMatch(quest -> !quest.isCompleted());
        boolean endGameQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame"))
                .allMatch(quest -> !quest.isCompleted());
        if (hero.getQuests().isEmpty()) {
            logger.info("""
                    You have arrived at the castle, but for now, you can't do anything more here.
                    Where would you like to go?
                    """);
        }
        else if (betrayedQuest) {
            betrayedQuest();
        } else if (endGameQuest) {
            endGameQuest();
        }
        locationVisitor.outsideLocationsChoice(city, locationVisitor);
    }
    private void betrayedQuest() {
        Hero hero = HeroContextHolder.getHero();
        logger.info("""
                    You enter the castle and immediately hear the screams of terrified, fleeing people.
                    You try to run towards the direction from which people are running, and eventually reach the source.
                    You see a creature, it's immense, but it's still a human.
                    What happened to him then? Did he feast on mushrooms and grow this big?
                    It doesn't matter, there's a reward on his head, so he must be dealt with!
                    """);
        Monster monster = new Elite("Herold", 7, 700, 200, 100, 0.1);
        fightService.performBattle(monster);
        if (hero.getCurrentHp() > 0) {
            Quest betrayed = hero.getQuests().stream()
                    .filter(quest -> quest.getName().equals("Betrayed")).findFirst().orElseThrow();
            betrayed.getLocations().replace("CastleLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("Betrayed"));
            quests.add(betrayed);
            hero.setQuests(quests);
            betrayed.isQuestCompleted();
            logger.info("""
                        After a truly fierce and incredibly tough battle, you finally managed to slay the oversized beast.
                        The reward for completing the quest is now yours.
                        """);
        }
        else {
            hero.setCurrentHp(hero.getMaxHp());
            logger.info("""
                    Beast was better than you, first you need to stronger.
                    """);
        }
    }
    private void endGameQuest() {
        Hero hero = HeroContextHolder.getHero();
        logger.info("""
                    So here comes our city's legend, hello hello.
                    People talk a lot about you, they are very impressed and thankful to you for helping liberate the city.
                    There's only one final battle left, with the king of monsters, and once you defeat him, long-awaited peace will come.
                    Everyone is counting on you, head to the forest and engage in the fight, then return to the mayor.
                    Good luck!
                    """);
        List<Quest> endGame = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame")).toList();
        if (!endGame.isEmpty()) {
            endGame.get(0).getLocations().replace("CastleLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("EndGame"));
            quests.add(endGame.get(0));
            endGame.get(0).isQuestCompleted();
            hero.setQuests(quests);
        }
    }
}
