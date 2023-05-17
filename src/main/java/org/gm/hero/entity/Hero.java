package org.gm.hero.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Hero {
    private String name;
    private HeroClass heroClass;
    private int lvl;
    private float hp;
    private float experience;
    private float requiredExperience;
    private int skillPoints;
    private Abilities abilities;
    private ModifierAbilities modifierAbilities;
    private AbilitiesAfterModifier abilitiesAfterModifier;

    public Hero(String name, HeroClass heroClass) {
        this.name = name;
        this.heroClass = heroClass;
        this.lvl = 1;
        this.hp = 100;
        this.experience = 0.0f;
        this.skillPoints = 10;
        this.abilities = new Abilities(1.0f,1.0f,1.0f,1.0f,1.0f,1.0f);
        this.modifierAbilities = new ModifierAbilities();
        this.abilitiesAfterModifier = new AbilitiesAfterModifier();
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", heroClass=" + heroClass +
                ", lvl=" + lvl +
                ", experience=" + experience +
                ", requiredExperience=" + requiredExperience +
                ", skillPoints=" + skillPoints +
                ", abilities=" + abilities +
                ", modifierAbilities=" + modifierAbilities +
                ", abilitiesAfterModifier=" + abilitiesAfterModifier +
                '}';
    }
}
