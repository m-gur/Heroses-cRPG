package org.gm.location.outside;

import org.gm.hero.entity.Hero;
import org.gm.location.city.CityLocation;

import java.util.Scanner;

public class CastleLocation extends OutsideLocation {
    public void explore(Hero hero, CityLocation city) {
        Scanner scanner = new Scanner(System.in);

        logger.info("""
                You have arrived at the castle, but for now, you can't do anything more here.
                Where would you like to go?
                """);
        initializeChoicesMap(hero, city);
        extracted(scanner);
    }
}
