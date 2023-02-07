package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CompareTwoHeroesPort;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.exceptions.HeroNotFoundException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CompareTwoHeroesServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);
    private final CompareTwoHeroesPort compareTwoHeroesPort = heroRepositoryInMemory;

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;


    private final CompareTwoHeroesService service = new CompareTwoHeroesService(compareTwoHeroesPort);

    @Test
    public void compareTwoHeroesWithSuccess() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Mumram", Race.ALIEN), secondPowerStatsId);

        //WHEN
        var result = service.compareTwoHeroes(firstHeroId, secondHeroId);

        //THEN
        assertEquals(firstHeroId, result.getFirstHeroId());
        assertEquals(secondHeroId, result.getSecondHeroId());
        assertEquals(0, result.getStrengthDifference());
        assertEquals(0, result.getAgilityDifference());
        assertEquals(0, result.getDexterityDifference());
        assertEquals(0, result.getIntelligenceDifference());
    }

    @Test
    public void compareTwoHeroesWhenFirstIdDoesNotExists() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var invalidId = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.compareTwoHeroes(firstHeroId, invalidId));
    }

    @Test
    public void compareTwoHeroesWhenSecondIdDoesNotExists() {
        //GIVEN
        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), secondPowerStatsId);

        var invalidId = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.compareTwoHeroes(invalidId, secondHeroId));
    }

    public PersistPowerStatsCommand createPersistPowerStatsCommand() {
        return new PersistPowerStatsCommand()
                .setStrength(5)
                .setAgility(5)
                .setDexterity(5)
                .setIntelligence(5);
    }

    public PersistHeroCommand createPersistHeroCommand(String name, Race race) {
        return new PersistHeroCommand()
                .setName(name)
                .setRace(race);
    }
}
