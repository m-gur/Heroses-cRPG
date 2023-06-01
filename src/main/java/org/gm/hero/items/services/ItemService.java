package org.gm.hero.items.services;

import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemService {
    private AbilitiesService abilitiesService = new AbilitiesService();

    public Map<ItemType, List<Item>> addItemToInventory(Hero hero, Item item) {
        ItemType itemType = item.getItemType();
        Map<ItemType, List<Item>> inventory = hero.getInventory();

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
        abilitiesService.setAbilitiesAfterModifier(hero);
    }
}
