package org.gm.fights;

import lombok.RequiredArgsConstructor;
import org.gm.factory.ItemFactory;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.Item;
import org.gm.hero.services.LevelService;
import org.gm.monster.Monster;
import org.gm.utils.HeroContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FightService {
    private final LevelService levelService;
    private final ItemFactory itemFactory;
    private static final Logger logger = LoggerFactory.getLogger(FightService.class);

    public void performBattle(Monster monster) {
        Hero hero = HeroContextHolder.getHero();
        Random random = new Random();
        boolean heroFirstAttack = random.nextBoolean();

        logger.info("The battle between the " + hero.getName() + " and the " + monster.getName() + " begins.");
        while (hero.getCurrentHp() > 0 && monster.getHp() > 0) {
            if (heroFirstAttack) {
                performHeroAttack(monster);
                if (monster.getHp() <= 0) {
                    afterHeroWon(monster);
                } else {
                    performMonsterAttack(monster);
                }
            } else {
                performMonsterAttack(monster);
                if (hero.getCurrentHp() <= 0) {
                    logger.info("Monster " + monster.getName() + " won the fight.");
                    break;
                } else {
                    performHeroAttack(monster);
                    if (monster.getHp() <= 0) {
                        afterHeroWon(monster);
                    }
                }
            }
        }
    }

    private void afterHeroWon(Monster monster) {
        Hero hero = HeroContextHolder.getHero();
        levelService.accumulateExperience(monster.getExperience());
        Item randomItem = itemFactory.createRandomItem();
        if (randomItem == null) {
            logger.info("Hero " + hero.getName() + " won the fight.");
            logger.info("Cannot add item to your inventory");
        } else {
            hero.setInventory(randomItem.addItemToInventory());
            logger.info("Hero " + hero.getName() + " won the fight.");
            logger.info("Received " + randomItem.getClass().getSimpleName() + " item: " + randomItem);
        }
    }

    private void performHeroAttack(Monster monster) {
        Hero hero = HeroContextHolder.getHero();
        attack(hero.getClass(), hero, monster);
    }

    private void performMonsterAttack(Monster monster) {
        Hero hero = HeroContextHolder.getHero();
        attack(monster.getClass(), hero, monster);
    }

    private void attack(Class attacker, Hero hero, Monster monster) {
        float damage = 0;
        if (attacker.equals(monster.getClass())) {
            damage = monster.getDamage();
        } else if (attacker.equals(hero.getClass())) {
            damage = hero.getDamage();
        }
        if (isCriticalHit(monster.getCriticalChance())) {
            double criticalDamageMultiplier = 1.8;
            damage *= criticalDamageMultiplier;
            if (attacker.equals(monster.getClass())) {
                logger.info("Critical hit! " + monster.getName() + " attack with: " + damage + " damage");
                setHeroHpAfterMonsterAttack(damage);
            } else if (attacker.equals(hero.getClass())) {
                logger.info("Critical hit! " + hero.getName() + " attack with: " + damage + " damage");
                setMonsterHpAfterHeroAttack(monster, damage);
            }
        } else {
            if (attacker.equals(monster.getClass())) {
                logger.info(monster.getName() + " attack with: " + damage + " damage");
                setHeroHpAfterMonsterAttack(damage);
            } else if (attacker.equals(hero.getClass())) {
                logger.info(hero.getName() + " attack with: " + damage + " damage");
                setMonsterHpAfterHeroAttack(monster, damage);
            }
        }
    }

    private void setHeroHpAfterMonsterAttack(float damage) {
        Hero hero = HeroContextHolder.getHero();
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
