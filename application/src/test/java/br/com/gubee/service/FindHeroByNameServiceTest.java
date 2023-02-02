package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.FindHeroByNamePort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.service.exceptions.HeroNotFoundException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindHeroByNameServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;
    private final FindHeroByNamePort findHeroByNamePort = heroRepositoryInMemory;

    private final FindHeroByNameService service = new FindHeroByNameService(findHeroByNamePort);

    @Test
    public void findHeroByNameWithSuccess() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Mumram", Race.ALIEN), secondPowerStatsId);

        //WHEN
        var result = service.findByName("Mumram");

        //THEN
        assertEquals(1, result.size());
        assertEquals("Mumram", result.get(0).getName());
    }

    @Test
    public void findHeroByNameWhenNameIsBlankAndListIsEmpty() {
        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.findByName(""));
    }

    @Test
    public void findHeroByNameWhenNameIsBlankAndListIsNotEmpty() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Mumram", Race.ALIEN), secondPowerStatsId);

        //WHEN
        var result = service.findByName("");

        //THEN
        assertEquals(2, result.size());
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
