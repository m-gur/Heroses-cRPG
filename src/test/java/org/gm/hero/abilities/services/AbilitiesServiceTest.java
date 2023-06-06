package org.gm.hero.abilities.services;

import org.gm.factory.HeroFactoryTest;
import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AbilitiesServiceTest {
    private HeroFactoryTest heroFactoryTest;
    private Abilities abilities;
    private AbilitiesService abilitiesService;

    @BeforeEach
    public void setUp() {
        heroFactoryTest = new HeroFactoryTest();
        abilitiesService = new AbilitiesService();
    }
    @Test
    void distributeSkillPoints_WithoutParameters_ReturnsTrue() {

        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        archer.setAbilities(abilities);
        archer.setSkillPoints(20);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 5);
        skillPointsDistribution.put("speed", 3);
        skillPointsDistribution.put("dexterity", 4);

        //when
        abilitiesService.distributeSkillPoints(archer, skillPointsDistribution);

        //then
        assertEquals(6, archer.getAbilities().getStrength());
        assertEquals(4, archer.getAbilities().getSpeed());
        assertEquals(5, archer.getAbilities().getDexterity());
    }

    @Test
    void distributeSkillPoints_WithoutParameters_ReturnsFalse() {

        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        archer.setAbilities(abilities);
        archer.setSkillPoints(20);
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 25);

        //when
        abilitiesService.distributeSkillPoints(archer, skillPointsDistribution);

        //then
        assertFalse(archer.getAbilities().getStrength() > 25);
    }
}