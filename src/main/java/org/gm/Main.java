package org.gm;

import org.gm.hero.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.HeroClass;
import org.gm.hero.services.AbilitiesService;
import org.gm.hero.services.HeroService;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        HeroService heroService = new HeroService();
        AbilitiesService abilitiesService = new AbilitiesService();
        Abilities abilities = new Abilities(1,1,1,1,1,1);
        Hero hero = new Hero();
        hero.setName("Archie");
        hero.setLvl(1);
        hero.setExperience(0.0f);
        hero.setSkillPoints(10);
        hero.setAbilities(abilities);
        hero.setHeroClass(HeroClass.ARCHER);
        hero.setRequiredExperience(heroService.calculateRequiredExperience(hero.getLvl()));
        System.out.println(hero);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 5);
        heroService.gainExperience(hero,1100);
        abilitiesService.distributeSkillPoints(hero, skillPointsDistribution);
        System.out.println(hero);
    }
}