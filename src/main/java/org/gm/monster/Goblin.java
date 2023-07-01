package org.gm.monster;

public class Goblin extends Monster {
    public Goblin(String name) {
        super(name);
    }

    public Goblin(String name, int lvl, float hp, float experience, float damage, double criticalChance) {
        super(name, lvl, hp, experience, damage, criticalChance);
    }
}
