package org.gm.location.outside;

import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.hero.entity.Hero;
import org.gm.location.Location;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;

public class OutsideLocation extends Location {
    protected final MonsterFactory monsterFactory = new MonsterFactory();
    protected final FightService fightService = new FightService();
    protected final LocationVisitor locationVisitor = new LocationVisitor();

    public void explore(Hero hero, CityLocation city) {
        logger.info("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
        locationVisitor.outsideLocationsChoice(hero, city);
    }
}
