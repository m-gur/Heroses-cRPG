package org.gm.monster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.hero.entity.Hero;
import org.gm.utils.HeroContextHolder;

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

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public void setExperience() {
        Hero hero = HeroContextHolder.getHero();
        this.setExperience((float) ((hero.getLvl() + this.getLvl()) * 1.3));
    }

}
