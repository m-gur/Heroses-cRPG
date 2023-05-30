package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.abilities.services.AbilitiesService;
import org.gm.hero.entity.Archer;
import org.gm.hero.entity.Hero;
import org.gm.hero.entity.Knight;
import org.gm.hero.entity.Mage;
import org.gm.hero.items.entity.Item;
import org.gm.hero.items.entity.ItemType;
import org.gm.hero.services.HeroService;
import org.gm.hero.services.LevelService;

import java.util.*;

public class HeroFactory {
    private ItemFactory itemFactory = new ItemFactory();
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
        Map<ItemType, List<Item>> inventory = new HashMap<>();
        List<Item> helmets = new ArrayList<>();
        helmets.add(itemFactory.createFactoryItem(ItemType.HELMET));
        List<Item> chests = new ArrayList<>();
        chests.add(itemFactory.createFactoryItem(ItemType.CHEST));
        List<Item> rings = new ArrayList<>();
        rings.add(itemFactory.createFactoryItem(ItemType.RING));
        List<Item> necklaces = new ArrayList<>();
        necklaces.add(itemFactory.createFactoryItem(ItemType.NECKLACE));
        List<Item> trousers = new ArrayList<>();
        trousers.add(itemFactory.createFactoryItem(ItemType.TROUSERS));
        List<Item> shoes = new ArrayList<>();
        shoes.add(itemFactory.createFactoryItem(ItemType.SHOES));
        List<Item> weapons = new ArrayList<>();
        weapons.add(itemFactory.createFactoryItem(ItemType.WEAPON));
        List<Item> usually = new ArrayList<>();
        usually.add(itemFactory.createFactoryItem(ItemType.USUALLY));
        List<Item> usable = new ArrayList<>();
        usable.add(itemFactory.createFactoryItem(ItemType.USABLE));
        inventory.put(ItemType.HELMET, helmets);
        inventory.put(ItemType.CHEST, chests);
        inventory.put(ItemType.RING, rings);
        inventory.put(ItemType.NECKLACE, necklaces);
        inventory.put(ItemType.TROUSERS, trousers);
        inventory.put(ItemType.SHOES, shoes);
        inventory.put(ItemType.WEAPON, weapons);
        inventory.put(ItemType.USUALLY, usually);
        inventory.put(ItemType.USABLE, usable);

        Map<ItemType, Item> equippedItems = new HashMap<>();
        equippedItems.put(ItemType.HELMET, itemFactory.createFactoryItem(ItemType.HELMET));
        equippedItems.put(ItemType.CHEST, itemFactory.createFactoryItem(ItemType.CHEST));
        equippedItems.put(ItemType.RING, itemFactory.createFactoryItem(ItemType.RING));
        equippedItems.put(ItemType.NECKLACE, itemFactory.createFactoryItem(ItemType.NECKLACE));
        equippedItems.put(ItemType.TROUSERS, itemFactory.createFactoryItem(ItemType.TROUSERS));
        equippedItems.put(ItemType.SHOES, itemFactory.createFactoryItem(ItemType.SHOES));
        equippedItems.put(ItemType.WEAPON, itemFactory.createFactoryItem(ItemType.WEAPON));
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
        hero.setInventory(inventory);
        hero.setEquippedItems(equippedItems);
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
