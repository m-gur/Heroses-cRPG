package org.gm.hero.services;

import org.gm.hero.entity.Hero;
import org.gm.utils.HeroContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    public float calculateRequiredExperience(int heroLevel) {
        return (float) heroLevel * 100;
    }

    public void accumulateExperience(float experiencePoints) {
        Hero hero = HeroContextHolder.getHero();
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints + experiencePoints);
        boolean levelUpNeeded = hero.getExperience() >= hero.getRequiredExperience();
        if (levelUpNeeded) {
            handleLevelUpIfNeeded();
        }
    }

    private void handleLevelUpIfNeeded() {
        Hero hero = HeroContextHolder.getHero();
        while (hero.getExperience() >= hero.getRequiredExperience()) {
            performLevelUp();
            float remainingExperience = hero.getExperience() - hero.getRequiredExperience();
            hero.setExperience(remainingExperience);
            hero.setRequiredExperience(calculateRequiredExperience(hero.getLvl()));
        }
    }

    private void performLevelUp() {
        Hero hero = HeroContextHolder.getHero();
        int currentLevel = hero.getLvl();
        hero.setLvl(currentLevel + 1);
        addSkillPointsAfterLevelUp();
        upgradeStatisticsAfterLevelUp();
    }

    private void addSkillPointsAfterLevelUp() {
        Hero hero = HeroContextHolder.getHero();
        int currentHeroSkillPoints = hero.getSkillPoints();
        int additionalSkillPoints = 10;
        hero.setSkillPoints(currentHeroSkillPoints + additionalSkillPoints);
    }

    private void upgradeStatisticsAfterLevelUp() {
        Hero hero = HeroContextHolder.getHero();
        hero.setDamage();
        hero.setMaxHp(hero.setHP());
        hero.setCurrentHp(hero.getMaxHp());
    }
}
