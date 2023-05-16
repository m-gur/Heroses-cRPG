package org.gm;

import org.gm.hero.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.HeroClass;
import org.gm.hero.services.HeroService;

public class Main {
    public static void main(String[] args) {

        HeroService heroService = new HeroService();
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
        heroService.gainExperience(hero,1100);
        System.out.println(hero);
    }
}