package org.gm.location.outside;

import org.gm.hero.entity.Hero;
import org.gm.hero.quest.Quest;
import org.gm.hero.quest.QuestService;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;

import java.util.List;
import java.util.Scanner;

public class HauntedForestLocation extends OutsideLocation {
    QuestService questService = new QuestService();
    @Override
    public void explore(Hero hero) {
        hero.setCurrentHp(10000);
        Scanner scanner = new Scanner(System.in);
        boolean firstJourneyQuest = hero.getQuests().stream()
                .anyMatch(quest -> quest.getName().equals("First Journey"));
        if (firstJourneyQuest) {
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
            Monster monster = new Monster("Cerber", MonsterClass.OTHER, 5, 400, 50, 60);
            fightService.performBattle(hero, monster);
            if (hero.getCurrentHp() > 0) {
                Quest firstJourney = hero.getQuests().stream()
                        .filter(quest -> quest.getName().equals("First Journey")).findFirst().orElseThrow();
                firstJourney.getLocations().replace("HauntedForestLocation", false, true);
                List<Quest> quests = hero.getQuests();
                quests.removeIf(quest -> quest.getName().equals("First Journey"));
                quests.add(firstJourney);
                hero.setQuests(quests);
                questService.isQuestCompleted(hero);
                logger.info("""
                        You have slain the beast.
                        It was a tough battle, but you managed to do it.
                        The reward for completing the quest is now yours.
                        """);
            }
        } else {
            logger.info("""
                    Currently, you are too weak for this place. Come back when you are stronger.
                    Where would you like to go?
                    """);
        }
        super.extracted(scanner);
    }
}
