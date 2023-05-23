package org.gm;

import org.gm.factory.HeroFactory;
import org.gm.fights.FightService;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.services.HeroService;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;
import org.gm.monster.services.MonsterService;

import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        HeroFactory heroFactory = new HeroFactory();
        AbilitiesService abilitiesService = new AbilitiesService();
        HeroService heroService = new HeroService();
        Hero hero = heroFactory.createHero("Archer");
        System.out.println(hero);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 5);
        skillPointsDistribution.put("speed", 3);
        skillPointsDistribution.put("dexterity", 4);
        abilitiesService.distributeSkillPoints(hero, skillPointsDistribution);
        System.out.println(hero);

        MonsterService monsterService = new MonsterService();
        Monster monster = new Monster("Higher Orc Commander", MonsterClass.ORC);
        monster.setLvl(1);
        monster.setHp(50);
        monster.setExperience(monsterService.setExperience(monster, hero));
        monster.setDamage(30);
        System.out.println(monster);

        FightService fightService = new FightService();
        fightService.performBattle(hero, monster);
        System.out.println(hero);

        heroService.restoreHP(hero, "HP Potion");
        System.out.println(hero);
        abilitiesService.resetSkillPoints(hero);
        System.out.println(hero);
    }
}