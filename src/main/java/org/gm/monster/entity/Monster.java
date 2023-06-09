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

    public Monster(String name, MonsterClass monsterClass) {
        this.name = name;
        this.monsterClass = monsterClass;
    }

    @Override
    public String toString() {
        return "Monster{" +
               "name='" + name + '\'' +
               ", monsterClass=" + monsterClass +
               ", lvl=" + lvl +
               ", hp=" + hp +
               ", experience=" + experience +
               ", damage=" + damage +
               '}';
    }
}
