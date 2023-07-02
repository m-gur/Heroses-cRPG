package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.location.LocationVisitor;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.springframework.stereotype.Component;

@Component
public class MarketLocation extends CityLocation {


    public MarketLocation(CharacterMenu characterMenu, GameMenu gameMenu) {
        super(characterMenu, gameMenu);
    }

    @Override
    public void explore(Hero hero, LocationVisitor locationVisitor) {
        logger.info("""
            The center of the town, from this place you can go to the mayor,
            sell some stuff to merchants, or lose your money for other things.
            """);
        locationVisitor.marketLocationsChoice(hero, locationVisitor);
    }

}
