package org.gm.location.outside;

import org.gm.hero.entity.Hero;

import java.util.Scanner;

public class HauntedForestLocation extends OutsideLocation {
    @Override
    public void explore(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        logger.info("""
        Currently, you are too weak for this place. Come back when you are stronger.
        Where would you like to go?
        """);

        extracted(scanner);
    }
}
