package org.gm.fights;

import org.gm.hero.entity.Archer;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FightServiceTest {
    @Mock
    private Archer archer;
    @Mock
    private Monster monster;
    @InjectMocks
    FightService fightService;

    @BeforeEach
    public void setUp() {
        archer = new Archer("Archie");

        monster = new Monster("Higher Orc Commander", MonsterClass.ORC);
        monster.setLvl(1);
        monster.setHp(100);
        monster.setExperience(100);
        monster.setDamage(30);

        fightService = new FightService();
    }

    @Test
     void testFight_WithoutParameters_HeroWins() {

        //given
        archer.setDamage(40);

        //when
        fightService.performBattle(archer, monster);


        //then
        assertTrue(archer.getCurrentHp() >= 0);
    }

    @Test
     void testFight_WithoutParameters_MonsterWins() {

        //given
        archer.setDamage(1);

        //when
        fightService.performBattle(archer, monster);

        //then
        assertTrue(archer.getCurrentHp() <= 0);
    }
}