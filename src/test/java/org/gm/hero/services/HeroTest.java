package org.gm.hero.services;

import org.gm.factory.HeroFactoryTest;
import org.gm.factory.ItemFactoryTest;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.Item;
import org.gm.hero.items.Usable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeroTest {
    private ItemFactoryTest itemFactoryTest;
    private HeroFactoryTest heroFactoryTest;

    @BeforeEach
    public void setUp() {
        itemFactoryTest = new ItemFactoryTest();
        heroFactoryTest = new HeroFactoryTest();
    }

    @Test
    void restoreHP_WithoutParameters_ReturnsTrue() {

        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        archer.setMaxHp(200);
        archer.setCurrentHp(100);
        Map<Class<? extends Item>, List<Item>> inventory = new HashMap<>();
        List<Item> usable = new ArrayList<>();
        usable.add(itemFactoryTest.createFactoryItem(Usable.class));
        inventory.put(Usable.class, usable);
        archer.setInventory(inventory);
        //when
        archer.restoreHP("HP Potion");

        //then
        assertEquals(200, archer.getCurrentHp());
    }

    @Test
    void restoreHP_WithoutParameters_ReturnsFalse() {

        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        archer.setMaxHp(200);
        archer.setCurrentHp(100);
        //when
        archer.restoreHP("HP Potion");

        //then
        assertEquals(100, archer.getCurrentHp());
    }
}