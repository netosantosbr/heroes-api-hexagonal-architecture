package br.com.gubee.service;

import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.exceptions.InvalidHeroException;
import br.com.gubee.exceptions.InvalidPowerStatsException;
import br.com.gubee.service.stubs.HeroRepositoryInMemory;
import br.com.gubee.service.stubs.PowerStatsRepositoryInMemory;
import br.com.gubee.usecase.command.CreateHeroCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateHeroServiceTest {
    PowerStatsRepositoryInMemory powerStatsRepositoryInMemory = new PowerStatsRepositoryInMemory();
    HeroRepositoryInMemory heroRepositoryInMemory = new HeroRepositoryInMemory(powerStatsRepositoryInMemory);

    private final CreateHeroPort createHeroPort = heroRepositoryInMemory;
    private final CreatePowerStatsPort createPowerStatsPort = powerStatsRepositoryInMemory;

    private final CreateHeroService service = new CreateHeroService(createHeroPort, createPowerStatsPort);

    @Test
    public void createHeroWithSuccess() {
        //GIVEN
        var heroCommand = newCreateHeroCommand("Memphis", Race.CYBORG);

        //WHEN
        var id = service.create(heroCommand);

        //THEN
        var objectToVerify = heroRepositoryInMemory.findById(id);
        assertNotNull(id);
        assertNotNull(objectToVerify);
        assertEquals("Memphis", objectToVerify.getName());
        assertEquals(Race.CYBORG, objectToVerify.getRace());
        assertEquals(2, objectToVerify.getStrength());
        assertEquals(3, objectToVerify.getAgility());
        assertEquals(2, objectToVerify.getDexterity());
        assertEquals(1, objectToVerify.getIntelligence());
    }

    @Test
    public void createHeroWithInvalidHeroParameters() {
        //GIVEN
        var heroCommand = newCreateHeroCommand("", Race.CYBORG);

        //WHEN - THEN
        assertThrows(InvalidHeroException.class, () -> service.create(heroCommand));
    }

    @Test
    public void createHeroWithInvalidPowerStatsParameters() {
        //GIVEN
        var heroCommand = newCreateHeroCommand("Memphis", Race.CYBORG);
        heroCommand.setIntelligence(20);
        heroCommand.setAgility(-5);

        //WHEN - THEN
        assertThrows(InvalidPowerStatsException.class, () -> service.create(heroCommand));
    }

    public CreateHeroCommand newCreateHeroCommand(String name, Race race) {
        return new CreateHeroCommand()
                .setName(name)
                .setRace(race)
                .setStrength(2)
                .setAgility(3)
                .setDexterity(2)
                .setIntelligence(1);
    }

}
