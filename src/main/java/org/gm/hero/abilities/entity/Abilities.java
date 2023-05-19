package org.gm.hero.abilities.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Abilities {
    private float strength;
    private float defence;
    private float intelligence;
    private float dexterity;
    private float agility;
    private float speed;

    @Override
    public String toString() {
        return "Abilities{" +
                "strength=" + strength +
                ", defence=" + defence +
                ", intelligence=" + intelligence +
                ", dexterity=" + dexterity +
                ", agility=" + agility +
                ", speed=" + speed +
                '}';
    }
}
