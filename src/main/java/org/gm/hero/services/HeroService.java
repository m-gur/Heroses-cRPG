package org.gm.hero.services;

import org.gm.hero.entity.Hero;

public class HeroService {

    public float calculateRequiredExperience(int heroLvl) {
        return (float) heroLvl * 100;
    }
    public void gainExperience(Hero hero, float experiencePoints) {
        float actualHeroExperiencePoints = hero.getExperience();
        hero.setExperience(actualHeroExperiencePoints+experiencePoints);
        checkLevelUp(hero);
    }

    private void checkLevelUp(Hero hero) {
        while (hero.getExperience() >= hero.getRequiredExperience()) {
                levelUp(hero);
                hero.setExperience(hero.getExperience() - hero.getRequiredExperience());
                hero.setRequiredExperience(calculateRequiredExperience(hero.getLvl()));
        }
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
