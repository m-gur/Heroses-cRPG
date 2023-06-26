package org.gm.monster.entity;

public class Skeleton extends Monster {
    public Skeleton(String name) {
        super(name);
    }

    public Skeleton(String name, int lvl, float hp, float experience, float damage, double criticalChance) {
        super(name, lvl, hp, experience, damage, criticalChance);
    }
}
