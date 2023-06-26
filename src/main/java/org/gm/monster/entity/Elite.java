package org.gm.monster.entity;

public class Elite extends Monster {
    public Elite(String name) {
        super(name);
    }

    public Elite(String name, int lvl, float hp, float experience, float damage, double criticalChance) {
        super(name, lvl, hp, experience, damage, criticalChance);
    }
}
