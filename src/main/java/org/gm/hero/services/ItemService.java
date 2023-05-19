package org.gm.hero.services;

import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Item;
import org.gm.hero.entity.ItemType;

import java.util.Map;

public class ItemService {
    public Map<ItemType, Item> itemOperation(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        Item currentlyEquippedItem = equippedItems.get(itemType);
        if (currentlyEquippedItem != null) {
            unequipItem(hero, currentlyEquippedItem);
        }
        equipItem(hero, item);
        return equippedItems;
    }
    public void unequipItem(Hero hero, Item item) {
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        ItemType itemType = item.getItemType();
        item.setUsage(false);
        equippedItems.remove(itemType);
    }
    private void equipItem(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        Map<ItemType, Item> equippedItems = hero.getEquippedItems();
        item.setUsage(true);
        equippedItems.put(itemType, item);
    }
}
