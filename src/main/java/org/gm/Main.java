package org.gm;

import org.gm.fights.FightService;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.*;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.services.HeroService;
import org.gm.hero.items.services.ItemService;
import org.gm.hero.services.LevelService;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;
import org.gm.monster.services.MonsterService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;



public class Main {
    public static void main(String[] args) {

        HeroService heroService = new HeroService();
        AbilitiesService abilitiesService = new AbilitiesService();
        ItemService itemService = new ItemService();
        LevelService levelService = new LevelService();
        Archer archer = new Archer("Archie");
        archer.setModifierAbilities(abilitiesService.setModifierAbilities(archer));
        archer.setAbilitiesAfterModifier(abilitiesService.setAbilitiesAfterModifier(archer));
        archer.setRequiredExperience(levelService.calculateRequiredExperience(archer.getLvl()));
        archer.setDamage(heroService.setDamage(archer));
        System.out.println(archer);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 5);
        skillPointsDistribution.put("speed", 3);
        skillPointsDistribution.put("dexterity", 4);
        levelService.accumulateExperience(archer,1100);
        abilitiesService.distributeSkillPoints(archer, skillPointsDistribution);
        System.out.println(archer);


        MonsterService monsterService = new MonsterService();
        Monster monster = new Monster("Higher Orc Commander", MonsterClass.ORC);
        monster.setLvl(1);
        monster.setHp(50);
        monster.setExperience(monsterService.setExperience(monster, archer));
        monster.setDamage(30);
        System.out.println(monster);


        FightService fightService = new FightService();
        fightService.performBattle(archer, monster);
        System.out.println(archer);

        Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        Item item = new Item("Bow", ItemType.WEAPON, abilities, BigDecimal.valueOf(10), 1, false);
        Item item2 = new Item("HP Potion", ItemType.USABLE, BigDecimal.valueOf(10), 2, false);
        Item item3 = new Item("HP Apple", ItemType.USABLE, BigDecimal.valueOf(10), 1, false);
        archer.setEquippedItems(itemService.itemOperation(archer, item));
        archer.setInventory(itemService.addItemToInventory(archer, item2));
        archer.setInventory(itemService.addItemToInventory(archer, item3));
        archer.setAbilitiesAfterModifier(abilitiesService.setAbilitiesAfterModifier(archer));
        System.out.println(archer);
        heroService.restoreHP(archer, "HP Potion");
        System.out.println(archer);


    }
}