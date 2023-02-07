package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.FindHeroByIdPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.exceptions.HeroNotFoundException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FindHeroByIdServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;
    private final FindHeroByIdPort findHeroByIdPort = heroRepositoryInMemory;

    private final FindHeroByIdService service = new FindHeroByIdService(findHeroByIdPort);

    @Test
    public void findHeroByIdWithSuccess() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Mumram", Race.ALIEN), secondPowerStatsId);

        //WHEN
        var result = service.findById(secondHeroId);

        //THEN
        assertNotNull(result);
        assertEquals("Mumram", result.getName());
        assertEquals(Race.ALIEN, result.getRace());
    }


    @Test
    public void findHeroByIdWhenIdDoesNotExists() {
        //GIVEN
        var invalidId = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.findById(invalidId));
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
