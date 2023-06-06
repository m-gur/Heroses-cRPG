package org.gm.fights;

import org.gm.factory.HeroFactoryTest;
import org.gm.hero.entity.Hero;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FightServiceTest {
    private HeroFactoryTest heroFactoryTest;
    private Monster monster;
    private FightService fightService;

    @BeforeEach
    public void setUp() {
        heroFactoryTest = new HeroFactoryTest();

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
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        archer.setDamage(40);

        //when
        fightService.performBattle(archer, monster);


        //then
        assertTrue(archer.getCurrentHp() >= 0);
    }

    @Test
     void testFight_WithoutParameters_MonsterWins() {

        //given
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        archer.setDamage(1);

        //when
        fightService.performBattle(archer, monster);

        //then
        assertTrue(archer.getCurrentHp() <= 0);
    }
}