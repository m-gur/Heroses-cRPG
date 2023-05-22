package org.gm.hero.abilities.services;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Archer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbilitiesServiceTest {

    @Mock
    private Archer archer;
    @Mock
    private Abilities abilities;
    @InjectMocks
    AbilitiesService abilitiesService;

    @BeforeEach
    public void setUp() {
        abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);
        archer = new Archer("Archie");
        archer.setAbilities(abilities);
        archer.setSkillPoints(20);
        abilitiesService = new AbilitiesService();
    }
    @Test
    void distributeSkillPoints_WithoutParameters_ReturnsTrue() {

        //given
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
        Map<String, Integer> skillPointsDistribution = new HashMap<>();
        skillPointsDistribution.put("strength", 25);

        //when
        abilitiesService.distributeSkillPoints(archer, skillPointsDistribution);

        //then
        assertFalse(archer.getAbilities().getStrength() > 25);
    }
}