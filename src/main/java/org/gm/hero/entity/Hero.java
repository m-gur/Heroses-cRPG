package org.gm.hero.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Hero {
    private String name;
    private HeroClass heroClass;
    private int lvl;
    private float experience;
    private float requiredExperience;
    private int skillPoints;
    private Abilities abilities;
    private ModifierAbilities modifierAbilities;
    private AbilitiesAfterModifier abilitiesAfterModifier;

    public Hero() {
        this.modifierAbilities = new ModifierAbilities();
        this.abilities = new Abilities(1.0f,1.0f,1.0f,1.0f,1.0f,1.0f);
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
