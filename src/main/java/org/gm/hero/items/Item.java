package org.gm.hero.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public abstract class Item {
    private String name;
    private Abilities abilities;
    private BigDecimal value;
    private int quantity;
    private boolean isUsage;
    private String itemType;

    public abstract String getItemType();

    public Item(String name, Abilities abilities, BigDecimal value, int quantity, boolean isUsage) {
        this.name = name;
        this.abilities = abilities;
        this.value = value;
        this.quantity = quantity;
        this.isUsage = isUsage;
        this.itemType = getItemType();
    }

    @Override
    public String toString() {
        return "Item{" +
               "name='" + name + '\'' +
               ", abilities=" + abilities +
               ", value=" + value +
               ", quantity=" + quantity +
               ", isUsage=" + isUsage +
               '}';
    }

    public Map<Class<? extends Item>, List<Item>> addItemToInventory(Hero hero) {
        Class<? extends Item> thisItemType = this.getClass();
        Map<Class<? extends Item>, List<Item>> inventory = hero.getInventory();

        if (inventory.containsKey(thisItemType)) {
            List<Item> itemList = inventory.get(thisItemType);
            itemList.add(this);
        } else {
            List<Item> itemList = new ArrayList<>();
            itemList.add(this);
            inventory.put(thisItemType, itemList);
        }

        return inventory;
    }

    public void sellItem(Hero hero) {
        Class<? extends Item> thisItemType = this.getClass();
        Map<Class<? extends Item>, List<Item>> inventory = hero.getInventory();
        List<Item> items = new ArrayList<>();
        items.add(this);
        if (this.isUsage()) {
            this.unequipItem(hero);
        }
        inventory.remove(thisItemType, items);
        BigDecimal thisValue = this.getValue();
        hero.setCoins(hero.getCoins().add(thisValue));
    }

    public void itemOperation(Hero hero) {
        Class<? extends Item> thisItemType = this.getClass();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        Item currentlyEquippedItem = equippedItems.get(thisItemType);
        if (currentlyEquippedItem != null) {
            this.unequipItem(hero);
        }
        this.equipItem(hero);
    }

    public void unequipItem(Hero hero) {
        AbilitiesService abilitiesService = new AbilitiesService();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        Class<? extends Item> thisItemType = this.getClass();
        this.setUsage(false);
        abilitiesService.unsetAbilitiesFromItems(hero, this);
        equippedItems.remove(thisItemType);
        abilitiesService.setAbilitiesAfterModifier(hero);
    }

    private void equipItem(Hero hero) {
        AbilitiesService abilitiesService = new AbilitiesService();
        Class<? extends Item> thisItemType = this.getClass();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        this.setUsage(true);
        equippedItems.put(thisItemType, this);
        abilitiesService.setAbilitiesAfterModifier(hero);
    }
}
