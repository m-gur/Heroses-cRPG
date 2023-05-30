package org.gm.fights;

import org.gm.factory.ItemFactory;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.services.ItemService;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;

import java.util.*;

public class FightService {
    private LevelService levelService = new LevelService();
    private ItemFactory itemFactory = new ItemFactory();
    private ItemService itemService = new ItemService();
    public void performBattle(Hero hero, Monster monster) {
        Random random = new Random();
        boolean heroFirstAttack = random.nextBoolean();

        while (hero.getCurrentHp() > 0 && monster.getHp() > 0) {
            if (heroFirstAttack) {
                performHeroAttack(hero, monster);
                if (monster.getHp() <= 0) {
                    levelService.accumulateExperience(hero, monster.getExperience());
                    Item randomItem = itemFactory.createRandomItem(hero);
                    hero.setInventory(itemService.addItemToInventory(hero, randomItem));
                } else {
                    performMonsterAttack(hero, monster);
                }
            } else {
                performMonsterAttack(hero, monster);
                if (hero.getCurrentHp() <= 0) {
                    break;
                } else {
                    performHeroAttack(hero, monster);
                    if (monster.getHp() <= 0) {
                        levelService.accumulateExperience(hero, monster.getExperience());
                        Item randomItem = itemFactory.createRandomItem(hero);
                        hero.setInventory(itemService.addItemToInventory(hero, randomItem));
                    }
                }
            }
        }
    }

    private void performHeroAttack(Hero hero, Monster monster) {
        monster.setHp(monster.getHp() - hero.getDamage());
    }

    private void performMonsterAttack(Hero hero, Monster monster) {
        hero.setCurrentHp(hero.getCurrentHp() - monster.getDamage());
    }


}
