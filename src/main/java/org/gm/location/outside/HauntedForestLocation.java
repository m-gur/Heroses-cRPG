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
public class HauntedForestLocation extends OutsideLocation {
    public HauntedForestLocation(MonsterFactory monsterFactory, FightService fightService) {
        super(monsterFactory, fightService);
    }

    public void explore(CityLocation city, LocationVisitor locationVisitor) {
        Hero hero = HeroContextHolder.getHero();
        boolean firstJourneyQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("First Journey"))
                .allMatch(quest -> !quest.isCompleted());
        boolean endGameQuest = hero.getQuests().stream()
                .filter(quest -> quest.getName().equals("EndGame"))
                .allMatch(quest -> !quest.isCompleted());
        if (hero.getQuests().isEmpty()) {
            logger.info("""
                    You don't have anything to look for here at the moment, please come back another time.
                    Where would you like to go?
                    """);
        }
        else if (firstJourneyQuest) {
            firstJourneyQuest();
        } else if (endGameQuest) {
            endGameQuest();
        }
        locationVisitor.outsideLocationsChoice(city, locationVisitor);
    }
    private void firstJourneyQuest() {
        Hero hero = HeroContextHolder.getHero();
        logger.info("""
                    You walk slowly through the forest, occasionally hearing a strange rustling sound.
                    You try not to pay it much attention and continue moving forward.
                    Suddenly, you hear something approaching rapidly.
                    It jumps out at you from behind a tree, but you manage to dodge it at the last moment and attempt to get a closer look at the beast.
                                        
                    It's enormous, resembling a mutated wolf, but with three heads.
                    Wait, it's Cerberus! Each head drools saliva, but it's not ordinary saliva—it has a faint glow to it.
                    It could be poisonous, so better be cautious.
                    You can see blood on its claws, indicating that the beast has recently claimed a victim.
                    Keep going, fight bravely!
                    """);
        Monster monster = new Elite("Cerber", 5, 400, 50, 60, 0.3);
        fightService.performBattle(monster);
        if (hero.getCurrentHp() > 0) {
            Quest firstJourney = hero.getQuests().stream()
                    .filter(quest -> quest.getName().equals("First Journey")).findFirst().orElseThrow();
            firstJourney.getLocations().replace("HauntedForestLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("First Journey"));
            quests.add(firstJourney);
            hero.setQuests(quests);
            firstJourney.isQuestCompleted();
            logger.info("""
                        You have slain the beast.
                        It was a tough battle, but you managed to do it.
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
                        You have reached the forest and see a horde of monsters.
                        One of them approaches you and says
                        "Welcome, our problem. We have been waiting for you. We are here to escort you to our ruler. Come, come with us."
                        
                        You follow the monsters through the forest until you reach an arena.
                        You step onto it, and before your eyes appears the king of monsters.
                        The main issue for the inhabitants, which you must resolve, is to confront him, defeat him, and bring peace to these lands.
                        Will you succeed? Everyone is counting on you!
                    """);
        Monster monster = new Elite("The King", 15, 5000, 500, 1000, 0.7);
        fightService.performBattle(monster);
        if (hero.getCurrentHp() > 0) {
            Quest endGame = hero.getQuests().stream()
                    .filter(quest -> quest.getName().equals("EndGame")).findFirst().orElseThrow();
            endGame.getLocations().replace("HauntedForestLocation", false, true);
            List<Quest> quests = hero.getQuests();
            quests.removeIf(quest -> quest.getName().equals("EndGame"));
            quests.add(endGame);
            hero.setQuests(quests);
            endGame.isQuestCompleted();
            logger.info("""
                        You 've won, congratulations! So, we must leave these lands, but we will meet again :)
                        """);
        }
        else {
            hero.setCurrentHp(hero.getMaxHp());
            logger.info("""
                    Beast was better than you, first you need to stronger.
                    """);
        }
    }
}
