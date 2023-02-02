package br.com.gubee.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PowerStatsTest {
    @Test
    public void createAValidPowerStats() {
        //GIVEN
        var powerStats = new PowerStats(5, 5, 5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertTrue(result);

    }

    @Test
    public void createAInvalidPowerStatsDefiningStrengthLowerThanZero() {
        //GIVEN
        var powerStats = new PowerStats(-5, 5, 5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);


    }

    @Test
    public void createAInvalidPowerStatsDefiningAgilityLowerThanZero() {
        //GIVEN
        var powerStats = new PowerStats(5, -5, 5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningDexterityLowerThanZero() {
        //GIVEN
        var powerStats = new PowerStats(5, 5, -5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningIntelligenceLowerThanZero() {
        //GIVEN
        var powerStats = new PowerStats(5, 5, 5, -5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningStrengthGreaterThanTen() {
        //GIVEN
        var powerStats = new PowerStats(12, 5, 5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningAgilityGreaterThanTen() {
        //GIVEN
        var powerStats = new PowerStats(5, 12, 5, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningDexterityGreaterThanTen() {
        //GIVEN
        var powerStats = new PowerStats(5, 5, 12, 5);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }

    @Test
    public void createAInvalidPowerStatsDefiningIntelligenceGreaterThanTen() {
        //GIVEN
        var powerStats = new PowerStats(5, 5, 5, 12);

        //WHEN
        var result = powerStats.validate();

        //THEN
        assertFalse(result);
    }
}
