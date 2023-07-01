package org.gm.hero.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.entity.AbilitiesAfterModifier;
import org.gm.hero.abilities.entity.ModifierAbilities;
import org.gm.hero.abilities.entity.ModifierStrategy;
import org.gm.hero.items.Item;
import org.gm.hero.items.Usable;
import org.gm.hero.quest.Quest;
import org.gm.utils.Utils;

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

    public float setDamage() {
        float baseDamage = 10f + (this.getLvl() * 10);
        AbilitiesAfterModifier abilities = this.getAbilitiesAfterModifier();

        if (this instanceof Mage) {
            this.setDamage(baseDamage + (abilities.getIntelligence() * 10));
        } else if (this instanceof Knight) {
            this.setDamage(baseDamage + (abilities.getStrength() * 10));
        } else if (this instanceof Archer) {
            this.setDamage(baseDamage + (abilities.getDexterity() * 10));
        } else {
            Utils.logger.info(Utils.INVALID);
        }

        return this.getDamage();
    }

    public float setHP() {
        float baseHP = 100f + (this.getLvl() * 10);
        AbilitiesAfterModifier abilities = this.getAbilitiesAfterModifier();

        if (this instanceof Mage) {
            this.setMaxHp(baseHP + (abilities.getDefence() * 13));
        } else if (this instanceof Knight) {
            this.setMaxHp(baseHP + (abilities.getDefence() * 20));
        } else if (this instanceof Archer) {
            this.setMaxHp(baseHP + (abilities.getDefence() * 15));
        } else {
            Utils.logger.info(Utils.INVALID);
        }

        return this.getMaxHp();
    }

    public void restoreHP(String itemName) {
        Map<Class<? extends Item>, List<Item>> actualInventory = this.getInventory();
        List<Item> usableItems = actualInventory.get(Usable.class);
        if (this.getCurrentHp() == this.getMaxHp()) {
            Utils.logger.info("You have max HP, cannot restore.");
        } else {
            if (usableItems != null) {
                for (Item item : usableItems) {
                    if (item.getName().equals(itemName)) {
                        if (item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1);
                        } else {
                            usableItems.remove(item);
                        }
                        this.setCurrentHp(this.getMaxHp());
                        Utils.logger.info("Hp restored successfully");
                        break;
                    }
                }
            }
        }
    }

}
