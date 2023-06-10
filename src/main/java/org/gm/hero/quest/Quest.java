package org.gm.hero.quest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Override
    public String toString() {
        return "Quest{" +
               "locations=" + locations.toString() +
               ", rewardExperience=" + rewardExperience +
               ", rewardCoins=" + rewardCoins +
               ", isCompleted=" + isCompleted +
               '}';
    }
}
