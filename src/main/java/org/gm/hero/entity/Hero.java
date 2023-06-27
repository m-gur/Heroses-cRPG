package org.gm.hero.entity;

import lombok.*;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.items.entity.Item;
import org.gm.hero.quest.Quest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public abstract class Hero {
    private String name;
    private int lvl;
    private float currentHp;
    private float maxHp;
    private float experience;
    private float requiredExperience;
    private int skillPoints;
    private Abilities abilities;
    private ModifierAbilities modifierAbilities;
    private AbilitiesAfterModifier abilitiesAfterModifier;
    private float damage;
    private Map<Class<? extends Item>, List<Item>> inventory;
    private Map<Class<? extends Item>, Item> equippedItems;
    private String heroType;
    private BigDecimal coins;
    private List<Quest> quests;
    private double criticalChance;

    public abstract ModifierStrategy getModifierStrategy();

    public abstract String getHeroType();

    public Hero(String name) {
        this.name = name;
        this.lvl = 1;
        this.currentHp = 100;
        this.maxHp = 100;
        this.experience = 0.0f;
        this.skillPoints = 10;
        this.abilities = new Abilities(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        this.modifierAbilities = new ModifierAbilities();
        this.abilitiesAfterModifier = new AbilitiesAfterModifier();
        this.inventory = new HashMap<>();
        this.equippedItems = new HashMap<>();
        this.heroType = getHeroType();
        this.coins = BigDecimal.ZERO;
        this.quests = new ArrayList<>();
        this.criticalChance = 0.1;
    }

    public Hero() {
        this.lvl = 1;
        this.experience = 0.0f;
        this.skillPoints = 10;
        this.abilities = new Abilities(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        this.modifierAbilities = new ModifierAbilities();
        this.abilitiesAfterModifier = new AbilitiesAfterModifier();
        this.inventory = new HashMap<>();
        this.equippedItems = new HashMap<>();
        this.heroType = getHeroType();
        this.coins = BigDecimal.ZERO;
        this.quests = new ArrayList<>();
        this.criticalChance = 0.1;
    }

}
