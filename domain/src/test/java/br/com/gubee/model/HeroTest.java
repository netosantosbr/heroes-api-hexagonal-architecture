package br.com.gubee.model;

import br.com.gubee.model.enums.Race;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class HeroTest {
    @Test
    public void createAValidHero() {
        //GIVEN
        Hero hero = new Hero("Morgan", Race.CYBORG, UUID.randomUUID());
        //WHEN
        var result = hero.validate();

        //THEN
        assertTrue(result);
    }

    @Test
    public void createAInvalidHeroUsingBlankName() {
        //GIVEN
        Hero hero = new Hero("", Race.CYBORG, UUID.randomUUID());
        //WHEN
        var result = hero.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidHeroUsingNullPowerStatsId() {
        //GIVEN
        Hero hero = new Hero("Morgan", Race.CYBORG, null);
        //WHEN
        var result = hero.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidHeroUsingNullRace() {
        //GIVEN
        Hero hero = new Hero("Morgan", null, UUID.randomUUID());
        //WHEN
        var result = hero.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidHeroUsingAllParametersNull() {
        //GIVEN
        Hero hero = new Hero(null, null, null);
        //WHEN
        var result = hero.validate();

        //THEN
        assertFalse(result);
    }
}
