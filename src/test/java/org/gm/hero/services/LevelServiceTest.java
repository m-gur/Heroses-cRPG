package org.gm.hero.services;

import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelServiceTest {
    private Hero hero;
    private LevelService levelService;
    @BeforeEach
    public void setUp() {
        levelService = new LevelService();
        hero = new Archer("Archie");
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));
    }
    @Test
    void accumulateExperience_withoutParameters_returnsTrue() {
        //given
        float experience = 1000;
        //when
        levelService.accumulateExperience(hero, experience);
        //then
        assertTrue(hero.getLvl() >= 4);
        assertTrue(hero.getExperience() <= 300);
    }
}