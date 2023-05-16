package org.gm.hero.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hero {
    private String name;
    private Integer lvl;
    private float experience;
    private Integer skillPoints;
    private Abilities abilities;
    private HeroClass heroClass;

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", lvl=" + lvl +
                ", experience=" + experience +
                ", skillPoints=" + skillPoints +
                ", abilities=" + abilities +
                ", heroClass=" + heroClass +
                '}';
    }
}
