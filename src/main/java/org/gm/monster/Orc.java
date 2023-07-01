package org.gm.monster;

public class Orc extends Monster {
    public Orc(String name) {
        super(name);
    }
    public Orc(String name , int lvl, float hp, float experience, float damage, double criticalChance) {
        super(name, lvl, hp, experience, damage, criticalChance);
    }

}
