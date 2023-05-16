package org.gm;

import org.gm.hero.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.HeroClass;
import org.gm.hero.services.HeroService;

public class Main {
    public static void main(String[] args) {

        HeroService heroService = new HeroService();
        Abilities abilities = new Abilities(1,1,1,1,1,1);
        Hero hero = new Hero("Archie", 1, 0.0f,10,abilities, HeroClass.ARCHER);
        System.out.println(hero);
        heroService.gainExperience(hero,1000);
        System.out.println(hero);
    }
}