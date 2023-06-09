package org.gm.hero.quest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gm.location.Location;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private Map<Location, Boolean> locations;
    private float rewardExperience;
    private BigDecimal rewardCoins;
    private boolean isCompleted;

    @Override
    public String toString() {
        return "Quest{" +
               "locations=" + locations +
               ", rewardExperience=" + rewardExperience +
               ", rewardCoins=" + rewardCoins +
               ", isCompleted=" + isCompleted +
               '}';
    }
}
