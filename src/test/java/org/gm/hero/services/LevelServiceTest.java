package org.gm.hero.services;

import org.gm.factory.HeroFactoryTest;
import org.gm.hero.entity.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelServiceTest {
    private HeroFactoryTest heroFactoryTest;
    private LevelService levelService;

    @BeforeEach
    public void setUp() {
        heroFactoryTest = new HeroFactoryTest();
        levelService = new LevelService();
    }

    @Test
    void accumulateExperience_withoutParameters_returnsTrue() {
        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        archer.setRequiredExperience(levelService.calculateRequiredExperience(archer.getLvl()));
        float experience = 1000;

        //when
        levelService.accumulateExperience(experience);
        //then
        assertTrue(archer.getLvl() >= 4);
        assertTrue(archer.getExperience() <= 300);
    }
}