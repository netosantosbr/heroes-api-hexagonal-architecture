package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.*;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.service.exceptions.HeroNotFoundException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteHeroServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);
    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;

    private final DeleteHeroPort deleteHeroPort = heroRepositoryInMemory;
    private final DeletePowerStatsPort deletePowerStatsPort = powerStatsRepositoryInMemory;


    private final FindPowerStatsIdFromHeroPort findPowerStatsIdFromHeroPort = heroRepositoryInMemory;
    private final DeleteHeroService service = new DeleteHeroService(deleteHeroPort, deletePowerStatsPort,
            findPowerStatsIdFromHeroPort);


    @Test
    public void deleteHeroWithSucess(){
        //GIVEN
        var powerStatsId = createPowerStatsPort.create(createPersistPowerStatsCommand());
        var heroId = createHeroPort.create(createPersistHeroCommand("Bob", Race.HUMAN), powerStatsId);
        var heroBefore = heroRepositoryInMemory.findById(heroId);

        //WHEN
        deleteHeroPort.delete(heroId);

        //THEN
        assertNotNull(heroBefore);
        assertThrows(NoSuchElementException.class, () -> heroRepositoryInMemory.findById(heroId));
    }

    @Test
    public void deleteHeroWhenIdNotExists(){
        //GIVEN
        var invalidId = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(HeroNotFoundException.class, () -> service.delete(invalidId));
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
