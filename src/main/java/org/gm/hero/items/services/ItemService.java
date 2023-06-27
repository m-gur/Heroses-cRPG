package org.gm.hero.items.services;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemService {
    private AbilitiesService abilitiesService = new AbilitiesService();

    public Map<Class<? extends Item>, List<Item>> addItemToInventory(Hero hero, Item item) {
        Class<? extends Item> itemType = item.getClass();
        Map<Class<? extends Item>, List<Item>> inventory = hero.getInventory();

        if (inventory.containsKey(itemType)) {
            List<Item> itemList = inventory.get(itemType);
            itemList.add(item);
        } else {
            List<Item> itemList = new ArrayList<>();
            itemList.add(item);
            inventory.put(itemType, itemList);
        }

        return inventory;
    }

    public void sellItem(Hero hero, Item item) {
        Class<? extends Item> itemType = item.getClass();
        Map<Class<? extends Item>, List<Item>> inventory = hero.getInventory();
        List<Item> items = new ArrayList<>();
        items.add(item);
        if (item.isUsage()) {
            unequipItem(hero, item);
        }
        inventory.remove(itemType, items);
        BigDecimal value = item.getValue();
        hero.setCoins(hero.getCoins().add(value));
    }

    public Map<Class<? extends Item>, Item> itemOperation(Hero hero, Item item) {
        Class<? extends Item> itemType = item.getClass();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        Item currentlyEquippedItem = equippedItems.get(itemType);
        if (currentlyEquippedItem != null) {
            unequipItem(hero, currentlyEquippedItem);
        }
        equipItem(hero, item);
        return equippedItems;
    }

    public void unequipItem(Hero hero, Item item) {
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        Class<? extends Item> itemType = item.getClass();
        item.setUsage(false);
        abilitiesService.unsetAbilitiesFromItems(hero, item);
        equippedItems.remove(itemType);
        abilitiesService.setAbilitiesAfterModifier(hero);
    }

    private void equipItem(Hero hero, Item item) {
        Class<? extends Item> itemType = item.getClass();
        Map<Class<? extends Item>, Item> equippedItems = hero.getEquippedItems();
        item.setUsage(true);
        equippedItems.put(itemType, item);
        abilitiesService.setAbilitiesAfterModifier(hero);
    }
}
