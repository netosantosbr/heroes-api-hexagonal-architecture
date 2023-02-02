package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.FindAllHeroPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindAllHeroServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;
    private final FindAllHeroPort findAllHeroPort = heroRepositoryInMemory;

    private final FindAllHeroService service = new FindAllHeroService(findAllHeroPort);

    @Test
    public void findAllHeroesWithSuccess() {
        //GIVEN
        var firstPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var firstHeroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), firstPowerStatsId);

        var secondPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var secondHeroId = createHeroPort.create(createPersistHeroCommand("Mumram", Race.ALIEN), secondPowerStatsId);

        var thirdPowerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var thirdHeroId = createHeroPort.create(createPersistHeroCommand("Toteah", Race.CYBORG), secondPowerStatsId);

        //WHEN
        var result = service.findAll();

        //THEN
        assertEquals(3, result.size());
        assertEquals("Bob", result.get(0).getName());
        assertEquals("Mumram", result.get(1).getName());
        assertEquals("Toteah", result.get(2).getName());
    }

    @Test
    public void findAllHeroesWhenListIsEmpty() {
        //WHEN
        var result = service.findAll();

        //THEN
        assertEquals(0, result.size());
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
