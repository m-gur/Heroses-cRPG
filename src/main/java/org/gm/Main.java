package org.gm;

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
        Hero hero = new Hero("Archie", HeroClass.ARCHER);
        hero.setModifierAbilities(heroService.setModifierAbilities(hero, hero.getHeroClass()));
        hero.setAbilitiesAfterModifier(abilitiesService.setAbilitiesAfterModifier(hero));
        hero.setRequiredExperience(heroService.calculateRequiredExperience(hero.getLvl()));
        hero.setDamage(heroService.setDamage(hero, hero.getHeroClass()));
        System.out.println(hero);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 5);
        skillPointsDistribution.put("speed", 3);
        skillPointsDistribution.put("dexterity", 4);
        heroService.gainExperience(hero,1100);
        abilitiesService.distributeSkillPoints(hero, skillPointsDistribution);
        System.out.println(hero);
    }
}