package org.gm.fights;

import org.gm.hero.entity.Hero;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;

import java.util.Random;

public class FightService {
    private LevelService levelService = new LevelService();
    public void performBattle(Hero hero, Monster monster) {
        Random random = new Random();
        boolean heroFirstAttack = random.nextBoolean();

        while (hero.getHp() > 0 && monster.getHp() > 0) {
            if (heroFirstAttack) {
                performHeroAttack(hero, monster);
                if (monster.getHp() <= 0) {
                    levelService.accumulateExperience(hero, monster.getExperience());
                } else {
                    performMonsterAttack(hero, monster);
                }
            } else {
                performMonsterAttack(hero, monster);
                if (hero.getHp() <= 0) {
                    break;
                } else {
                    performHeroAttack(hero, monster);
                    if (monster.getHp() <= 0) {
                        levelService.accumulateExperience(hero, monster.getExperience());
                    }
                }
            }
        }
    }

    private void performHeroAttack(Hero hero, Monster monster) {
        monster.setHp(monster.getHp() - hero.getDamage());
    }

    private void performMonsterAttack(Hero hero, Monster monster) {
        hero.setHp(hero.getHp() - monster.getDamage());
    }


}
