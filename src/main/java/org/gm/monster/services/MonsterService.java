package org.gm.monster.services;

import org.gm.hero.entity.Hero;
import org.gm.monster.entity.Monster;

public class MonsterService {

    public float setExperience(Monster monster, Hero hero) {
        monster.setExperience((float) ((hero.getLvl() + monster.getLvl()) * 1.3));
        return monster.getExperience();
    }
}
