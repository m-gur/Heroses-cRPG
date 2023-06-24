package org.gm.monster.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Monster {
    private String name;
    private MonsterClass monsterClass;
    private int lvl;
    private float hp;
    private float experience;
    private float damage;
    private double criticalChance;

    public Monster(String name, MonsterClass monsterClass) {
        this.name = name;
        this.monsterClass = monsterClass;
        this.criticalChance = 0.1;
    }

}
