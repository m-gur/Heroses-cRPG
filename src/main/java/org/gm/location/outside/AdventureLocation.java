package org.gm.location.outside;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.gm.monster.Monster;
import org.springframework.stereotype.Component;

@Component
public class AdventureLocation extends OutsideLocation {
    public AdventureLocation(MonsterFactory monsterFactory, FightService fightService) {
        super(monsterFactory, fightService);
    }

    public void explore(Hero hero, CityLocation city, LocationVisitor locationVisitor) {
        Monster randomMonster = monsterFactory.createRandomMonster(hero);
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
            city.explore(hero, locationVisitor);
        }
        locationVisitor.outsideLocationsChoice(hero, city, locationVisitor);
    }
}
