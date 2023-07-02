package org.gm.hero.services;

import org.gm.hero.entity.Hero;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    public float calculateRequiredExperience(int heroLevel) {
        return (float) heroLevel * 100;
    }

    public void accumulateExperience(Hero hero, float experiencePoints) {
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints + experiencePoints);
        boolean levelUpNeeded = hero.getExperience() >= hero.getRequiredExperience();
        if (levelUpNeeded) {
            handleLevelUpIfNeeded(hero);
        }
    }

    private void handleLevelUpIfNeeded(Hero hero) {
        while (hero.getExperience() >= hero.getRequiredExperience()) {
            performLevelUp(hero);
            float remainingExperience = hero.getExperience() - hero.getRequiredExperience();
            hero.setExperience(remainingExperience);
            hero.setRequiredExperience(calculateRequiredExperience(hero.getLvl()));
        }
    }

    private void performLevelUp(Hero hero) {
        int currentLevel = hero.getLvl();
        hero.setLvl(currentLevel + 1);
        addSkillPointsAfterLevelUp(hero);
        upgradeStatisticsAfterLevelUp(hero);
    }

    private void addSkillPointsAfterLevelUp(Hero hero) {
        int currentHeroSkillPoints = hero.getSkillPoints();
        int additionalSkillPoints = 10;
        hero.setSkillPoints(currentHeroSkillPoints + additionalSkillPoints);
    }

    private void upgradeStatisticsAfterLevelUp(Hero hero) {
        hero.setDamage();
        hero.setMaxHp(hero.setHP());
        hero.setCurrentHp(hero.getMaxHp());
    }
}
