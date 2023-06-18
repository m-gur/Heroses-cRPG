package org.gm.location.outside;

import org.gm.hero.entity.Hero;
import org.gm.location.city.CityLocation;
import org.gm.monster.entity.Monster;

import java.util.Scanner;

public class AdventureLocation extends OutsideLocation {
    public void explore(Hero hero, CityLocation city) {
        Monster randomMonster = monsterFactory.createRandomMonster(hero);
        Scanner scanner = new Scanner(System.in);
        logger.info("""
                You are brave, choosing an adventure.
                Are you able to find something more than just monsters here?
                """);
        fightService.performBattle(hero, randomMonster);
        if (hero.getCurrentHp() <= 0) {
            logger.info("""
                    You have died and have been transported to the main location in the game.
                    Your health has been restored.
                    """);
            hero.setCurrentHp(hero.getMaxHp());
            city.explore(hero);
        }
        initializeChoicesMap(hero, city);
        extracted(scanner);
    }
}
