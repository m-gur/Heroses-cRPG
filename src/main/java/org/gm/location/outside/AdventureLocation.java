package org.gm.location.outside;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.gm.monster.Monster;
import org.gm.utils.HeroContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AdventureLocation extends OutsideLocation {
    public AdventureLocation(MonsterFactory monsterFactory, FightService fightService) {
        super(monsterFactory, fightService);
    }

    public void explore(CityLocation city, LocationVisitor locationVisitor) {
        Hero hero = HeroContextHolder.getHero();
        Monster randomMonster = monsterFactory.createRandomMonster();
        logger.info("""
                You are brave, choosing an adventure.
                Are you able to find something more than just monsters here?
                """);
        fightService.performBattle(randomMonster);
        if (hero.getCurrentHp() <= 0) {
            logger.info("""
                    You have died and have been transported to the main location in the game.
                    Your health has been restored.
                    """);
            hero.setCurrentHp(hero.getMaxHp());
            city.explore(locationVisitor);
        }
        locationVisitor.outsideLocationsChoice(city, locationVisitor);
    }
}
