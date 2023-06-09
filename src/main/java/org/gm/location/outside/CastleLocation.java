package org.gm.location.outside;

import org.gm.hero.entity.Hero;

import java.util.Scanner;

public class CastleLocation extends OutsideLocation {
    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        logger.info("""
                You have arrived at the castle, but for now, you can't do anything more here.
                Where would you like to go?
                """);

        extracted(scanner);
    }
}
