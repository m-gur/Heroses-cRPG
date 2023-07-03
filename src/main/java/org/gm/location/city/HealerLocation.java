package org.gm.location.city;

import org.gm.hero.entity.Hero;
import org.gm.location.LocationVisitor;
import org.gm.menu.CharacterMenu;
import org.gm.menu.GameMenu;
import org.gm.utils.HeroContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HealerLocation extends CityLocation {

    public HealerLocation(CharacterMenu characterMenu, GameMenu gameMenu) {
        super(characterMenu, gameMenu);
    }

    @Override
    public void explore(LocationVisitor locationVisitor) {
        Hero hero = HeroContextHolder.getHero();
        logger.info("""
                Hello newcomer, your efforts are admired, and in return, all your health points have been restored.
                """);
        hero.setCurrentHp(hero.getMaxHp());
    }
}
