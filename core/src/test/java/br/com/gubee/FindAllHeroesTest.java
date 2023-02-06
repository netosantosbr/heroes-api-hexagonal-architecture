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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class FindAllHeroesTest extends PostgresSQLContainerConfig {
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
    public void findAllHeroesSuccessfullyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var firstId = persistHeroIntoDatabase("Zack", Race.HUMAN, 2, 4, 5, 6);
        var secondId = persistHeroIntoDatabase("Orion", Race.DIVINE, 3, 2, 1, 4);
        var thirdId = persistHeroIntoDatabase("Udyr", Race.ALIEN, 5, 3, 1, 9);
        var formattedURI = BASE_URL + "/findAll";

        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HeroRespIn>>(){});

        //THEN
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstId, response.getBody().get(0).getId());
        assertEquals(secondId, response.getBody().get(1).getId());
        assertEquals(thirdId, response.getBody().get(2).getId());
        assertEquals(Race.HUMAN, response.getBody().get(0).getRace());
        assertEquals(Race.DIVINE, response.getBody().get(1).getRace());
        assertEquals(Race.ALIEN, response.getBody().get(2).getRace());
        assertEquals(2, response.getBody().get(0).getStrength());
        assertEquals(3, response.getBody().get(1).getStrength());
        assertEquals(5, response.getBody().get(2).getStrength());
    }

    @Test
    public void findAllHeroesWhenListIsEmptyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var formattedURI = BASE_URL + "/findAll";

        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HeroRespIn>>(){});

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
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
