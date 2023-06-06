package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.services.HeroService;
import org.gm.hero.services.LevelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeroFactoryTest {
    private LevelService levelService = new LevelService();
    private HeroService heroService = new HeroService();
    private AbilitiesService abilitiesService = new AbilitiesService();
    public Hero createRandomHero(String heroType) {
        switch (heroType) {
            case "Mage":
                return randomMage();
            case "Knight":
                return randomKnight();
            case "Archer":
                return randomArcher();
            default:
                throw new IllegalArgumentException("Invalid hero type: " + heroType);
        }
    }

    private Hero createRandomHeroPartTwo(Class<? extends Hero> heroClass, String heroName) {
        Random random = new Random();
        Abilities abilities = new Abilities(1f, 1f, 1f, 1f, 1f, 1f);

        Hero hero;
        try {
            hero = heroClass.getConstructor(String.class).newInstance(heroName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        hero.setDamage(heroService.setDamage(hero));
        hero.setLvl(random.nextInt(10) + 1);
        hero.setExperience(random.nextFloat(99) + 1);
        hero.setMaxHp(heroService.setHP(hero));
        hero.setCurrentHp(hero.getMaxHp());
        hero.setAbilities(abilities);
        hero.setModifierAbilities(abilitiesService.setModifierAbilities(hero));
        hero.setAbilitiesAfterModifier(abilitiesService.setAbilitiesAfterModifier(hero));
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
        Random random = new Random();
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
        Random random = new Random();
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
        Random random = new Random();
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
