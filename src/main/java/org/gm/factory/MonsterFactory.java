package org.gm.factory;

import org.gm.hero.entity.Hero;
import org.gm.monster.Goblin;
import org.gm.monster.Monster;
import org.gm.monster.Orc;
import org.gm.monster.Skeleton;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class MonsterFactory {
    private Random random = new Random();

    public Monster createRandomMonster(Hero hero) {
        int monsterType = random.nextInt(3);

        Map<Integer, Function<Hero, Monster>> monsterMap = new HashMap<>();
        monsterMap.put(0, this::randomOrc);
        monsterMap.put(1, this::randomGoblin);
        monsterMap.put(2, this::randomSkeleton);

        return monsterMap.getOrDefault(monsterType, h -> {
            throw new IllegalArgumentException("Invalid monster type");
        }).apply(hero);
    }

    private Monster createRandomMonsterPartTwo(Monster monster, Hero hero) {
        monster.setLvl(random.nextInt(hero.getLvl() + 5) + 1);
        monster.setHp(random.nextFloat(hero.getMaxHp() + 50) + 1);
        monster.setExperience(hero);
        monster.setDamage(random.nextFloat(hero.getDamage() + 20) + 1);
        return monster;
    }

    private Monster randomOrc(Hero hero) {
        return createRandomMonsterPartTwo(new Orc(randomOrcName()), hero);
    }

    private Monster randomGoblin(Hero hero) {
        return createRandomMonsterPartTwo(new Goblin(randomGoblinName()), hero);
    }

    private Monster randomSkeleton(Hero hero) {
        return createRandomMonsterPartTwo(new Skeleton(randomSkeletonName()), hero);
    }

    private String randomOrcName() {
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
