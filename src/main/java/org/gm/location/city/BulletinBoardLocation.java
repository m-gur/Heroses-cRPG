package org.gm.location.city;

import org.gm.hero.entity.Hero;

public class BulletinBoardLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        logger.info("""
                Here you will see available quests, some are available now,
                others after completing the previous ones.
                """);
    }
}
