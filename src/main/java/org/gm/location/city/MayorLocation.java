package org.gm.location.city;

import org.gm.hero.entity.Hero;

public class MayorLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        logger.info("""
                Currently, you cannot meet with the mayor.
                You don't have any business with him, and he also doesn't want to see you at this moment.
                Please come back later.
                """);
    }
}
