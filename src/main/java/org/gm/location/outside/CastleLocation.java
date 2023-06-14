package org.gm.location.outside;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;
import org.gm.location.city.CityLocation;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;

import java.util.List;
import java.util.Scanner;

public class CastleLocation extends OutsideLocation {
    public void explore(Hero hero, CityLocation city) {
        Scanner scanner = new Scanner(System.in);
        boolean betrayedQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("Betrayed"))
                .allMatch(quest -> !quest.isCompleted());
        boolean endGameQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame"))
                .allMatch(quest -> !quest.isCompleted());
        if (betrayedQuest) {
            logger.info("""
                    You enter the castle and immediately hear the screams of terrified, fleeing people.
                    You try to run towards the direction from which people are running, and eventually reach the source.
                    You see a creature, it's immense, but it's still a human.
                    What happened to him then? Did he feast on mushrooms and grow this big?
                    It doesn't matter, there's a reward on his head, so he must be dealt with!
                    """);
            Monster monster = new Monster("Herold", MonsterClass.OTHER, 7, 700, 200, 100);
            fightService.performBattle(hero, monster);
            if (hero.getCurrentHp() > 0) {
                Quest firstJourney = hero.getQuests().stream()
                        .filter(quest -> quest.getName().equals("Betrayed")).findFirst().orElseThrow();
                firstJourney.getLocations().replace("CastleLocation", false, true);
                List<Quest> quests = hero.getQuests();
                quests.removeIf(quest -> quest.getName().equals("Betrayed"));
                quests.add(firstJourney);
                hero.setQuests(quests);
                questService.isQuestCompleted(hero);
                logger.info("""
                        After a truly fierce and incredibly tough battle, you finally managed to slay the oversized beast.
                        The reward for completing the quest is now yours.
                        """);
            }
        } else if (endGameQuest) {
            logger.info("""
                    So here comes our city's legend, hello hello.
                    People talk a lot about you, they are very impressed and thankful to you for helping liberate the city.
                    There's only one final battle left, with the king of monsters, and once you defeat him, long-awaited peace will come.
                    Everyone is counting on you, head to the forest and engage in the fight, then return to the mayor.
                    Good luck!
                    """);
            Quest firstJourney = hero.getQuests().stream()
                    .filter(quest -> quest.getName().equals("EndGame")).findFirst().orElseThrow();
            firstJourney.getLocations().replace("CastleLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("EndGame"));
            quests.add(firstJourney);
            hero.setQuests(quests);
        } else {
            logger.info("""
                    You have arrived at the castle, but for now, you can't do anything more here.
                    Where would you like to go?
                    """);
        }
        initializeChoicesMap(hero, city);
        extracted(scanner);
    }
}
