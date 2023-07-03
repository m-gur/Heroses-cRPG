package org.gm.location.outside;

import lombok.RequiredArgsConstructor;
import org.gm.factory.MonsterFactory;
import org.gm.fights.FightService;
import org.gm.location.Location;
import org.gm.location.LocationVisitor;
import org.gm.location.city.CityLocation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutsideLocation extends Location {
    protected final MonsterFactory monsterFactory;
    protected final FightService fightService;

    public void explore(CityLocation city, LocationVisitor locationVisitor) {
        logger.info("""
                You left town, feel a light breeze. Now you have to be careful.
                Monsters can be everywhere, where you want to go?
                """);
        locationVisitor.outsideLocationsChoice(city, locationVisitor);
    }
}
