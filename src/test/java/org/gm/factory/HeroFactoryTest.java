package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.services.LevelService;
import org.gm.utils.HeroContextHolder;

import java.util.*;
import java.util.function.Supplier;

public class HeroFactoryTest {
    private final LevelService levelService = new LevelService();
    private final AbilitiesService abilitiesService = new AbilitiesService();
    private Random random = new Random();

    public Hero createRandomHero(String heroType) {
        Map<String, Supplier<Hero>> heroMap = new HashMap<>();
        heroMap.put("Mage", this::randomMage);
        heroMap.put("Knight", this::randomKnight);
        heroMap.put("Archer", this::randomArcher);

        return heroMap.getOrDefault(heroType, () -> {
            throw new IllegalArgumentException("Invalid hero type: " + heroType);
        }).get();
    }


    private Hero createRandomHeroPartTwo(Class<? extends Hero> heroClass, String heroName) {
        Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);

        Hero hero;
        try {
            hero = heroClass.getConstructor(String.class).newInstance(heroName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        HeroContextHolder.setHero(hero);
        hero.setDamage();
        hero.setLvl(random.nextInt(10) + 1);
        hero.setExperience(random.nextFloat(99) + 1);
        hero.setMaxHp(hero.setHP());
        hero.setCurrentHp(hero.getMaxHp());
        hero.setAbilities(abilities);
        hero.setModifierAbilities(abilitiesService.setModifierAbilities());
        hero.setAbilitiesAfterModifier(abilitiesService.setAbilitiesAfterModifier());
        hero.setRequiredExperience(levelService.calculateRequiredExperience(hero.getLvl()));

        return hero;
    }

    private Mage randomMage() {
        return (Mage) createRandomHeroPartTwo(Mage.class, randomWizardName());
    }

    private Knight randomKnight() {
        return (Knight) createRandomHeroPartTwo(Knight.class, randomKnightName());
    }

    private Archer randomArcher() {
        return (Archer) createRandomHeroPartTwo(Archer.class, randomArcherName());
    }

    private String randomWizardName() {
        List<String> names = new ArrayList<>();
        names.add("Ineas");
        names.add("Urass");
        names.add("Sarish");
        names.add("Aloronin");
        names.add("Obras");
        names.add("Rhuprix");
        names.add("Urrasior");
        names.add("Ahion");
        names.add("Abras");
        names.add("Zobras");
        return names.get(random.nextInt(9));
    }

    private String randomKnightName() {
        List<String> names = new ArrayList<>();
        names.add("Lylie the Mild");
        names.add("Guinevere the Young");
        names.add("Elspet the Harbinger");
        names.add("Janat the Earnest");
        names.add("Marioth of the North");
        names.add("Gui the Shadow");
        names.add("Mainardus the Confident");
        names.add("Renout of the Mountains");
        names.add("Salemon the Swift");
        names.add("Ferrand the Keen");
        return names.get(random.nextInt(9));
    }

    private String randomArcherName() {
        List<String> names = new ArrayList<>();
        names.add("Ashitaka");
        names.add("Raj Kaur");
        names.add("Cheng Ming");
        names.add("Odysseus");
        names.add("Golden Archer");
        names.add("Hua Rong");
        names.add("Hanzo Shimada");
        names.add("Parashurama");
        names.add("Huntress");
        names.add("Ellie");
        return names.get(random.nextInt(9));
    }
}
