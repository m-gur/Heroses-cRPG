package org.gm.utils;

import org.gm.hero.items.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Utils {
    protected static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static void printItems(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            logger.info((i) + ". " + items.get(i));
        }
    }
}
