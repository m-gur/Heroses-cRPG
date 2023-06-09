package org.gm.location.city;

import org.gm.hero.entity.Hero;

public class HealerLocation extends CityLocation {
    @Override
    public void explore(Hero hero) {
        logger.info("""
                Hello newcomer, your efforts are admired, and in return, all your health points have been restored.
                """);
        hero.setCurrentHp(hero.getMaxHp());
    }
}
