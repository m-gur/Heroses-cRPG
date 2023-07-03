package org.gm.utils;

import org.gm.hero.entity.Hero;

public class HeroContextHolder {
    private static final ThreadLocal<Hero> context = new ThreadLocal<>();

    public static Hero getHero() {
        return context.get();
    }

    public static void setHero(Hero hero) {
        context.set(hero);
    }
}
