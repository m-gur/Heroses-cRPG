package org.gm.fights;

import org.gm.factory.ItemFactory;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.services.ItemService;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FightService {
    private LevelService levelService = new LevelService();
    private ItemFactory itemFactory = new ItemFactory();
    private ItemService itemService = new ItemService();
    private static final Logger logger = LoggerFactory.getLogger(FightService.class);

    public void performBattle(Hero hero, Monster monster) {
        Random random = new Random();
        boolean heroFirstAttack = random.nextBoolean();

        while (hero.getCurrentHp() > 0 && monster.getHp() > 0) {
            if (heroFirstAttack) {
                performHeroAttack(hero, monster);
                if (monster.getHp() <= 0) {
                    afterHeroWon(hero, monster);
                } else {
                    performMonsterAttack(hero, monster);
                }
            } else {
                performMonsterAttack(hero, monster);
                if (hero.getCurrentHp() <= 0) {
                    logger.info("Monster " + monster.getName() + " won the fight.");
                    break;
                } else {
                    performHeroAttack(hero, monster);
                    if (monster.getHp() <= 0) {
                        afterHeroWon(hero, monster);
                    }
                }
            }
        }
    }

    private void afterHeroWon(Hero hero, Monster monster) {
        levelService.accumulateExperience(hero, monster.getExperience());
        Item randomItem = itemFactory.createRandomItem(hero);
        hero.setInventory(itemService.addItemToInventory(hero, randomItem));
        logger.info("Hero " + hero.getName() + " won the fight.");
        logger.info("Received item: " + randomItem);
    }

    private void performHeroAttack(Hero hero, Monster monster) {
        attack("hero", hero, monster);
    }

    private void performMonsterAttack(Hero hero, Monster monster) {
        attack("monster", hero, monster);
    }

    private void attack(String attacker, Hero hero, Monster monster) {
        float damage = 0;
        if (attacker.equals("monster")) {
            damage = monster.getDamage();
        } else if (attacker.equals("hero")) {
            damage = hero.getDamage();
        }
        if (isCriticalHit(monster.getCriticalChance())) {
            double criticalDamageMultiplier = 1.8;
            damage *= criticalDamageMultiplier;
            if (attacker.equals("monster")) {
                logger.info("Critical hit! " + monster.getName() + " attack with: " + damage + " damage");
                setHeroHpAfterMonsterAttack(hero, damage);
            } else if (attacker.equals("hero")) {
                logger.info("Critical hit! " + hero.getName() + " attack with: " + damage + " damage");
                setMonsterHpAfterHeroAttack(monster, damage);
            }
        } else {
            if (attacker.equals("monster")) {
                logger.info(monster.getName() + " attack with: " + damage + " damage");
                setHeroHpAfterMonsterAttack(hero, damage);
            } else if (attacker.equals("hero")) {
                logger.info(hero.getName() + " attack with: " + damage + " damage");
                setMonsterHpAfterHeroAttack(monster, damage);
            }
        }
    }

    private void setHeroHpAfterMonsterAttack(Hero hero, float damage) {
        hero.setCurrentHp(hero.getCurrentHp() - damage);
        logger.info("Remaining hero health: " + hero.getCurrentHp());
    }

    private void setMonsterHpAfterHeroAttack(Monster monster, float damage) {
        monster.setHp(monster.getHp() - damage);
        logger.info("Remaining monster health: " + monster.getHp());
    }

    private boolean isCriticalHit(double criticalChance) {
        double randomValue = Math.random();
        return randomValue <= criticalChance;
    }

}
