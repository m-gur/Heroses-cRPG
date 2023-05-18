package org.gm.fights;

import org.gm.hero.entity.Hero;
import org.gm.hero.services.HeroService;
import org.gm.monster.entity.Monster;

import java.util.Random;

public class FightService {
    private HeroService heroService = new HeroService();
    public void fight(Hero hero, Monster monster) {
        Random random = new Random();
        boolean firstAttack = random.nextBoolean();
        boolean fightEnd = false;

        if (firstAttack) {
            while (!fightEnd) {
                monster.setHp(monster.getHp() - hero.getDamage());
                if (monster.getHp() <= 0) {
                    heroService.gainExperience(hero, monster.getExperience());
                    fightEnd = true;
                } else {
                    hero.setHp(hero.getHp() - monster.getDamage());
                    if (hero.getHp() <= 0) {
                        fightEnd = true;
                    }
                }
            }
        } else {
            while (!fightEnd) {
                hero.setHp(hero.getHp() - monster.getDamage());
                if (hero.getHp() <= 0) {
                    fightEnd = true;
                } else {
                    monster.setHp(monster.getHp() - hero.getDamage());
                    if (monster.getHp() <= 0) {
                        heroService.gainExperience(hero, monster.getExperience());
                        fightEnd = true;
                    }
                }
            }
        }
    }
}
