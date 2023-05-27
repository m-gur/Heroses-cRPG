package org.gm.factory;

import org.gm.hero.entity.Hero;
import org.gm.monster.entity.Monster;
import org.gm.monster.entity.MonsterClass;
import org.gm.monster.services.MonsterService;

import java.util.*;

public class MonsterFactory {
    MonsterService monsterService = new MonsterService();

    public Monster createRandomMonster(Hero hero) {
        Random random = new Random();
        int monsterType = random.nextInt(3);
        return switch (monsterType) {
            case 0 -> randomOrc(hero);
            case 1 -> randomGoblin(hero);
            case 2 -> randomSkeleton(hero);
            default -> throw new IllegalArgumentException("Invaild monster type");
        };
    }

    private Monster createRandomMonsterPartTwo(MonsterClass monsterClass, String monsterName, Hero hero) {
        Random random = new Random();
        Monster monster = new Monster(monsterName, monsterClass);

        monster.setLvl(random.nextInt(hero.getLvl() + 5)+1);
        monster.setHp(random.nextFloat(hero.getMaxHp() + 50)+1);
        monster.setExperience(monsterService.setExperience(monster, hero));
        monster.setDamage(random.nextFloat(hero.getDamage() + 20)+1);
        return monster;
    }

    private Monster randomOrc(Hero hero) {
        return createRandomMonsterPartTwo(MonsterClass.ORC, randomOrcName(), hero);
    }

    private Monster randomGoblin(Hero hero) {
        return createRandomMonsterPartTwo(MonsterClass.GOBLIN, randomGoblinName(), hero);
    }

    private Monster randomSkeleton(Hero hero) {
        return createRandomMonsterPartTwo(MonsterClass.SKELETON, randomSkeletonName(), hero);
    }

    private String randomOrcName() {
        Random random = new Random();
        List<String> names = new ArrayList<>();
        names.add("Higher Orc Commander");
        names.add("Orc Commander");
        names.add("Weak Orc");
        names.add("Higher Orc");
        names.add("King Orc");
        names.add("Orc");
        return names.get(random.nextInt(6));
    }

    private String randomGoblinName() {
        Random random = new Random();
        List<String> names = new ArrayList<>();
        names.add("Goblin");
        names.add("King Goblin");
        names.add("Weak Goblin");
        names.add("White Hand Goblin");
        names.add("Bloody Goblin");
        names.add("Goblin Commander");
        return names.get(random.nextInt(6));
    }

    private String randomSkeletonName() {
        Random random = new Random();
        List<String> names = new ArrayList<>();
        names.add("Skeleton");
        names.add("King Skeleton");
        names.add("Weak Skeleton");
        names.add("Black Skeleton");
        names.add("Taller Skeleton");
        names.add("Commander Skeleton");
        return names.get(random.nextInt(6));
    }
}
