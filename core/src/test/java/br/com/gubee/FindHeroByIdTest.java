package br.com.gubee;

import br.com.gubee.configuration.PostgresSQLContainerConfig;
import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.repository.HeroRepository;
import br.com.gubee.usecase.model.HeroRespIn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class FindHeroByIdTest extends PostgresSQLContainerConfig {
    private String BASE_URL;
    @LocalServerPort
    private Integer port;

    @Autowired
    private CreateHeroPort createHeroPort;

    @Autowired
    private CreatePowerStatsPort createPowerStatsPort;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void executeBeforeEachTestMethod() {
        BASE_URL = String.format("http://localhost:%s/api/v1/heroes", port);
    }

    @AfterEach
    public void executeAfterEachTestMethod(){
        cleanDatabase();
    }

    @Test
    public void findHeroByIdSuccessfullyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var heroId = persistHeroIntoDatabase("Arches", Race.ALIEN, 2, 3, 4, 5);
        var formattedURI = BASE_URL + "/" + heroId;

        //WHEN
        var response = restTemplate.getForEntity(formattedURI, HeroRespIn.class);

        //THEN
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Arches", response.getBody().getName());
        assertEquals(Race.ALIEN, response.getBody().getRace());
        assertEquals(2, response.getBody().getStrength());
        assertEquals(3, response.getBody().getAgility());
        assertEquals(4, response.getBody().getDexterity());
        assertEquals(5, response.getBody().getIntelligence());

    }

    @Test
    public void findHeroByIdWhenHeroDoesNotExistsAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var heroId = UUID.randomUUID();
        var formattedURI = BASE_URL + "/" + heroId;

        //WHEN
        var response = restTemplate.getForEntity(formattedURI, HeroRespIn.class);

        //THEN
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    public UUID persistHeroIntoDatabase(String name, Race race, int str, int agi, int dex, int intel) {
        var powerStatsId = createPowerStatsPort.create(new PersistPowerStatsCommand()
                .setStrength(str)
                .setAgility(agi)
                .setDexterity(dex)
                .setIntelligence(intel));
        var heroId = createHeroPort.create(new PersistHeroCommand()
                .setName(name)
                .setRace(race), powerStatsId);

        return heroId;
    }

    public void cleanDatabase() {
        heroRepository.findAll().forEach(hero -> heroRepository.delete(hero.getId()));
    }
}
