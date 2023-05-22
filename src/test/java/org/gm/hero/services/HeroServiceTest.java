package org.gm.hero.services;

import org.gm.hero.entity.Archer;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.items.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HeroServiceTest {

    @Mock
    private Archer archer;

    @Mock
    private Item item;

    @Mock
    private ItemService itemService;

    @InjectMocks
    HeroService heroService;

    @BeforeEach
    public void setUp() {
        item = new Item("HP Potion", ItemType.USABLE, BigDecimal.valueOf(10), 2, false);
        archer = new Archer("Archie");
        archer.setMaxHp(200);
        archer.setCurrentHp(100);
        itemService = new ItemService();
        heroService = new HeroService();
    }

    @Test
    void restoreHP_WithoutParameters_ReturnsTrue() {

        //given
        archer.setInventory(itemService.addItemToInventory(archer, item));

        //when
        heroService.restoreHP(archer, "HP Potion");

        //then
        assertEquals(200, archer.getCurrentHp());
    }

    @Test
    void restoreHP_WithoutParameters_ReturnsFalse() {

        //given

        //when
        heroService.restoreHP(archer, "HP Potion");

        //then
        assertEquals(100, archer.getCurrentHp());
    }
}