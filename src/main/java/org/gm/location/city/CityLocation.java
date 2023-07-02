package org.gm.location.city;

import lombok.RequiredArgsConstructor;
import org.gm.hero.entity.Hero;
import org.gm.location.Location;
import org.gm.location.LocationVisitor;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class CityLocation extends Location {
    protected static final String INVALID = "Invalid choice. Please try again.";
    protected final CharacterMenu characterMenu;
    protected final GameMenu gameMenu;

    public void explore(Hero hero, LocationVisitor locationVisitor) {
        logger.info("""
                You are in the city center, from this place everything begins.
                """);
        locationVisitor.cityLocationsChoice(hero, this, locationVisitor);
    }

}
