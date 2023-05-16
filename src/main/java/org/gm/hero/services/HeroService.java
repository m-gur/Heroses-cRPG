package org.gm.hero.services;

import org.gm.hero.entity.Hero;

public class HeroService {

    public void gainExperience(Hero hero, float experiencePoints) {
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints+experiencePoints);
        checkLevelUp(hero);
    }

    private void checkLevelUp(Hero hero) {
        float requiredExperience = calculateRequiredExperience(hero.getLvl());
        while (hero.getExperience() >= requiredExperience) {
            levelUp(hero);
            requiredExperience = calculateRequiredExperience(hero.getLvl());
        }
    }

    private float calculateRequiredExperience(int heroLvl) {
            return 100 * heroLvl * ((float) heroLvl / 10);
    }

    private void levelUp(Hero hero) {
        Integer actualHeroLvl = hero.getLvl();
        hero.setLvl(actualHeroLvl+1);
        addSkillPointsAfterLevelUp(hero);
    }

    private void addSkillPointsAfterLevelUp(Hero hero) {
        Integer actualHeroSkillPoints = hero.getSkillPoints();
        hero.setSkillPoints(actualHeroSkillPoints+10);
    }
}
