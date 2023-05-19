package org.gm.hero.entity;

import lombok.*;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private float damage;
    private List<Item> inventory;
    private Map<ItemType, Item> equippedItems;

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
        this.setDamage(10);
        this.inventory = new ArrayList<>();
        this.equippedItems = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", heroClass=" + heroClass +
                ", lvl=" + lvl +
                ", hp=" + hp +
                ", experience=" + experience +
                ", requiredExperience=" + requiredExperience +
                ", skillPoints=" + skillPoints +
                ", abilities=" + abilities +
                ", modifierAbilities=" + modifierAbilities +
                ", abilitiesAfterModifier=" + abilitiesAfterModifier +
                ", damage=" + damage +
                ", inventory=" + inventory +
                ", equippedItems=" + equippedItems +
                '}';
    }
}
