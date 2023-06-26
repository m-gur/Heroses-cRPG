package org.gm.monster.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Monster {
    private String name;
    private int lvl;
    private float hp;
    private float experience;
    private float damage;
    private double criticalChance;

    public Monster(String name) {
        this.name = name;
        this.criticalChance = 0.1;
    }

}
