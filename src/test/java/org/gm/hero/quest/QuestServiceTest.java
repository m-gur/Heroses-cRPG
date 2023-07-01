package org.gm.hero.quest;

import org.gm.factory.HeroFactoryTest;
import org.gm.hero.entity.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestServiceTest {

    private HeroFactoryTest heroFactoryTest;

    @BeforeEach
    public void setUp() {
        heroFactoryTest = new HeroFactoryTest();
    }
    @Test
    void isQuestCompleted_withoutParameters_returnsFalse() {
        //given
        Map<String, Boolean> locations = new LinkedHashMap<>();
        locations.put("testLocation", false);
        Quest quest = new Quest("Test", locations, 100, BigDecimal.valueOf(100));
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        List<Quest> quests = archer.getQuests();
        quests.add(quest);
        archer.setQuests(quests);
        //when
        quest.isQuestCompleted(archer);
        //then
        assertFalse(archer.getQuests().get(0).isCompleted());
    }

    @Test
    void isQuestCompleted_withoutParameters_returnsTrue() {
        //given
        Map<String, Boolean> locations = new LinkedHashMap<>();
        locations.put("testLocation", true);
        Quest quest = new Quest("Test", locations, 100, BigDecimal.valueOf(100));
        Hero archer = heroFactoryTest.createRandomHero("Archer");
        List<Quest> quests = archer.getQuests();
        quests.add(quest);
        archer.setQuests(quests);
        //when
        quest.isQuestCompleted(archer);
        //then
        assertTrue(archer.getQuests().get(0).isCompleted());
    }
}