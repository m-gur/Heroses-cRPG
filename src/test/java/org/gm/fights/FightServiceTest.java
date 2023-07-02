package org.gm.fights;

import org.gm.factory.HeroFactoryTest;
import org.gm.factory.ItemFactory;
import org.gm.hero.entity.Hero;
import org.gm.hero.services.LevelService;
import org.gm.monster.Monster;
import org.gm.monster.Orc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FightServiceTest {
    private HeroFactoryTest heroFactoryTest;
    private Monster monster;
    @Mock
    private ItemFactory itemFactory;
    @Mock
    private LevelService levelService;

    @InjectMocks
    private FightService fightService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        heroFactoryTest = new HeroFactoryTest();

        monster = new Orc("Higher Orc Commander");
        monster.setLvl(1);
        monster.setHp(100);
        monster.setExperience(100);
        monster.setDamage(30);
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