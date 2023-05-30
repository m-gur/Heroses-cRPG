package org.gm.fights;

import org.gm.factory.ItemFactory;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;

import java.util.*;

public class FightService {
    private LevelService levelService = new LevelService();
    private ItemFactory itemFactory = new ItemFactory();
    public void performBattle(Hero hero, Monster monster) {
        Random random = new Random();
        List<Item> itemList = new ArrayList<>();
        Map<ItemType, List<Item>> listMap = new HashMap<>();
        boolean heroFirstAttack = random.nextBoolean();

        while (hero.getCurrentHp() > 0 && monster.getHp() > 0) {
            if (heroFirstAttack) {
                performHeroAttack(hero, monster);
                if (monster.getHp() <= 0) {
                    levelService.accumulateExperience(hero, monster.getExperience());
                    Item randomItem = itemFactory.createRandomItem(hero);
                    itemList.add(randomItem);
                    listMap.put(randomItem.getItemType(), itemList);
                    hero.setInventory(listMap);
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
                        itemList.add(randomItem);
                        listMap.put(randomItem.getItemType(), itemList);
                        hero.setInventory(listMap);
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
