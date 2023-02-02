package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.UpdateHeroPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.service.exceptions.HeroNotFoundException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import br.com.gubee.usecase.model.HeroRespIn;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateHeroServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;

    private final UpdateHeroPort updateHeroPort = heroRepositoryInMemory;

    private final UpdateHeroService service = new UpdateHeroService(updateHeroPort);

    @Test
    public void updateHeroWithSuccess() {
        //GIVEN
        var powerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var heroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.CYBORG), powerStatsId);
        var heroRespIn = new HeroRespIn()
                .setName("Optimum")
                .setRace(Race.ALIEN)
                .setStrength(3)
                .setAgility(2)
                .setDexterity(3)
                .setIntelligence(1);
        var objectBefore = heroRepositoryInMemory.findById(heroId);

        //WHEN
        var result = service.update(heroId, heroRespIn);

        //THEN
        var objectAfter = heroRepositoryInMemory.findById(heroId);
        assertNotNull(objectBefore);
        assertEquals("Bob", objectBefore.getName());
        assertEquals(Race.CYBORG, objectBefore.getRace());
        assertNotNull(objectAfter);
        assertEquals("Optimum", objectAfter.getName());
        assertEquals(Race.ALIEN, objectAfter.getRace());
        assertEquals(3, objectAfter.getStrength());
        assertEquals(2, objectAfter.getAgility());
        assertEquals(3, objectAfter.getDexterity());
        assertEquals(1, objectAfter.getIntelligence());
    }

    @Test
    public void updateHeroWhenIdDoesNotExists() {
        //GIVEN
        var heroRespIn = new HeroRespIn()
                .setName("Optimum")
                .setRace(Race.ALIEN)
                .setStrength(3)
                .setAgility(2)
                .setDexterity(3)
                .setIntelligence(1);
        var invalidId = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.update(invalidId, heroRespIn));
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
