package org.gm.location;

import org.gm.hero.entity.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class Location {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void explore(Hero hero) {}
}
