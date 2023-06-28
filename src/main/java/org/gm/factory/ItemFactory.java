package org.gm.factory;

import org.gm.hero.abilities.entity.Abilities;
import org.gm.hero.entity.Hero;
import org.gm.hero.items.entity.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;

public class ItemFactory {
    private Random random = new Random();

    public Item createRandomItem(Hero hero) {
        Abilities abilitiesForArmorItems = new Abilities();
        abilitiesForArmorItems.setStrength(random.nextInt(hero.getLvl() * 3));
        abilitiesForArmorItems.setDefence(random.nextInt(hero.getLvl() * 3));
        abilitiesForArmorItems.setIntelligence(random.nextInt(hero.getLvl() * 3));
        abilitiesForArmorItems.setDexterity(random.nextInt(hero.getLvl() * 3));
        abilitiesForArmorItems.setAgility(random.nextInt(hero.getLvl() * 3));
        abilitiesForArmorItems.setSpeed(random.nextInt(hero.getLvl() * 3));
        Abilities abilitiesForOtherTypeItem = new Abilities(0f, 0f, 0f, 0f, 0f, 0f);
        int itemChoice = random.nextInt(9) + 1;

        Map<Integer, Supplier<Item>> itemCreators = new HashMap<>();
        itemCreators.put(1, () -> new Helmet(getRandomHelmetName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(2, () -> new Chest(getRandomChestName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(3, () -> new Ring(getRandomRingName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(4, () -> new Necklace(getRandomNecklaceName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(5, () -> new Trousers(getRandomTrousersName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(6, () -> new Shoes(getRandomShoesName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(7, () -> new Weapon(getRandomWeaponName(), abilitiesForArmorItems,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(8, () -> new Usually(getRandomUsuallyName(), abilitiesForOtherTypeItem,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));
        itemCreators.put(9, () -> new Usable(getRandomUsableName(), abilitiesForOtherTypeItem,
                BigDecimal.valueOf(random.nextInt(hero.getLvl() * 3)), 1, false));

        Supplier<Item> itemCreator = itemCreators.get(itemChoice);
        if (itemCreator != null) {
            return itemCreator.get();
        } else {
            throw new IllegalArgumentException("Invalid item type");
        }
    }


    private String getRandomHelmetName() {
        List<String> names = new ArrayList<>();
        names.add("Incarnated Ivory Helm");
        names.add("Jaws of Lost Souls");
        names.add("Visage of Faded Fires");
        names.add("Headguard of Unholy Hope");
        names.add("Skeletal Headguard of Cursed Warlords");
        names.add("Iron Headcover of Infernal Kings");
        names.add("Demonic Plate Headcover");
        names.add("Casque of the Leviathan");
        names.add("Bronze Helm of Silent Hell");
        names.add("Curator of Grace");
        return names.get(random.nextInt(9));
    }

    private String getRandomChestName() {
        List<String> names = new ArrayList<>();
        names.add("Vindicator Skeletal Batteplate");
        names.add("Warden of the King");
        names.add("Fire Infused Chestguard of Visions");
        names.add("Obsidian Vest of Distant Honor");
        names.add("Solitude's Armor of the Forest");
        names.add("Pledge of Fallen Souls");
        names.add("Terror Hide Breastplate");
        names.add("Vest of Dark Misery");
        names.add("Jerkin of Ancient Hells");
        names.add("Embroided Tunic");
        return names.get(random.nextInt(9));
    }

    private String getRandomRingName() {
        List<String> names = new ArrayList<>();
        names.add("Platinum Ring of Devotions");
        names.add("Ring of Fiend Finding");
        names.add("Ring of Whale Song");
        names.add("Worthy Eye Ring");
        names.add("Happy Twist Ring");
        names.add("Handsome Swan Ring");
        names.add("Virtuous Poem Ring");
        names.add("Crystal Image Ring");
        names.add("Peaceful Riddle Ring");
        names.add("Loyal Bauble Ring");
        return names.get(random.nextInt(9));
    }

    private String getRandomNecklaceName() {
        List<String> names = new ArrayList<>();
        names.add("Loyal Blossom Necklace");
        names.add("Azure Ambition Amulet");
        names.add("Zircon Belle Amulet");
        names.add("Zircon Resolve Necklace");
        names.add("Harmonious Blossom Necklace");
        names.add("Bright Spiral Amulet");
        names.add("Curly Shield Necklace");
        names.add("Bright Promise Necklace");
        names.add("Earnest Mark Necklace");
        names.add("Lunar Hope Necklace");
        return names.get(random.nextInt(9));
    }

    private String getRandomTrousersName() {
        List<String> names = new ArrayList<>();
        names.add("Leggings of Agony");
        names.add("Scaled Legplates of Frozen Wars");
        names.add("Kilt of Relentless Dreams");
        names.add("Demonic Linen Kilt");
        names.add("Legguards of Immortal Hope");
        names.add("Incarnation of Fools");
        names.add("Shadow Kilt of the Boar");
        names.add("Skirt of Binding Fire");
        names.add("Battleworn Robes of Danger");
        names.add("Foe of Infinite Trials");
        return names.get(random.nextInt(9));
    }

    private String getRandomShoesName() {
        List<String> names = new ArrayList<>();
        names.add("Stompers of Immortal Protection");
        names.add("Demonic Scaled Stompers");
        names.add("Challenger's Bone Boots");
        names.add("Frenzied Linen Footpads");
        names.add("Faithful Silk Sprinters");
        names.add("Treads of Damned Fire");
        names.add("Vindicator Heels of Stone");
        names.add("Obsidian Walkers");
        names.add("Spurs of Fleeting Bloodlust");
        names.add("Thunderfury Walkers of Stone");
        return names.get(random.nextInt(9));
    }

    private String getRandomWeaponName() {
        List<String> names = new ArrayList<>();
        names.add("Doom");
        names.add("Oblivion");
        names.add("Vacancy");
        names.add("Shadowmoon");
        names.add("Harbinger");
        names.add("Orenmir");
        names.add("Armageddon");
        names.add("Nirvana");
        names.add("Midnight");
        names.add("Hellfire");
        return names.get(random.nextInt(9));
    }

    private String getRandomUsuallyName() {
        List<String> names = new ArrayList<>();
        names.add("Photo");
        names.add("Rag Doll");
        names.add("Trophy");
        names.add("Scrap");
        names.add("Unidentified");
        return names.get(random.nextInt(5));
    }

    private String getRandomUsableName() {
        List<String> names = new ArrayList<>();
        names.add("HP Potion");
        names.add("HP Apple");
        names.add("HP Big Potion");
        names.add("HP Cup");
        names.add("HP Bread");
        return names.get(random.nextInt(5));
    }
}
