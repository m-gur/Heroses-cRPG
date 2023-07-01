package org.gm.hero.quest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.hero.entity.Hero;
import org.gm.hero.services.LevelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private String name;
    private Map<String, Boolean> locations;
    private float rewardExperience;
    private BigDecimal rewardCoins;
    private boolean isCompleted;

    public Quest(String name, Map<String, Boolean> locations, float rewardExperience, BigDecimal rewardCoins) {
        this.name = name;
        this.locations = locations;
        this.rewardExperience = rewardExperience;
        this.rewardCoins = rewardCoins;
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        return "Quest{" +
               "name='" + name + '\'' +
               ", locations=" + locations +
               ", rewardExperience=" + rewardExperience +
               ", rewardCoins=" + rewardCoins +
               ", isCompleted=" + isCompleted +
               '}';
    }

    public void isQuestCompleted(Hero hero) {
        LevelService levelService = new LevelService();
        List<Quest> quests = hero.getQuests();
        for (Quest quest : quests) {
            boolean allLocationsCompleted = quest.getLocations().values().stream()
                    .allMatch(Boolean::booleanValue);

            if (allLocationsCompleted) {
                quest.setCompleted(true);
                levelService.accumulateExperience(hero, quest.getRewardExperience());
                hero.setCoins(hero.getCoins().add(quest.getRewardCoins()));
            }
        }
    }
}
