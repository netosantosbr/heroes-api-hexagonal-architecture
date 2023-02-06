package br.com.gubee;

import br.com.gubee.configuration.PostgresSQLContainerConfig;
import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.repository.HeroRepository;
import br.com.gubee.usecase.DeleteHeroUseCase;
import br.com.gubee.usecase.model.HeroCompareRespIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class CompareTwoHeroesTest extends PostgresSQLContainerConfig {

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
    public void executeBeforeEachTestMethod(){
        BASE_URL = String.format("http://localhost:%s/api/v1/heroes", port);
        cleanDatabase();
    }

    @Test
    public void compareTwoHeroesSuccessfullyAndCheckItsStatusCodeAndResponseBody(){
        //GIVEN
        var firstId = persistHeroIntoDatabase("Frederico", Race.HUMAN);
        var secondId = persistHeroIntoDatabase("Amanion", Race.CYBORG);
        var formattedURI = BASE_URL + "/compare?firstId=" + firstId + "&secondId=" + secondId;

        //WHEN
        ResponseEntity<HeroCompareRespIn> response = restTemplate.getForEntity(formattedURI, HeroCompareRespIn.class);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstId, response.getBody().getFirstHeroId());
        assertEquals(secondId, response.getBody().getSecondHeroId());
        assertEquals(0, response.getBody().getStrengthDifference());
        assertEquals(0, response.getBody().getAgilityDifference());
        assertEquals(0, response.getBody().getDexterityDifference());
        assertEquals(0, response.getBody().getIntelligenceDifference());
    }

    @Test
    public void compareTwoHeroesWhenIdDoesNotExistsAndCheckItsStatusCodeAndResponseBody(){
        //GIVEN
        var firstId = UUID.randomUUID();
        var secondId = persistHeroIntoDatabase("Amanion", Race.CYBORG);
        var formattedURI = BASE_URL + "/compare?firstId=" + firstId + "&secondId=" + secondId;

        //WHEN
        ResponseEntity<HeroCompareRespIn> response = restTemplate.getForEntity(formattedURI, HeroCompareRespIn.class);

        //THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }


    public UUID persistHeroIntoDatabase(String name, Race race) {
        var powerStatsId = createPowerStatsPort.create(new PersistPowerStatsCommand()
                .setStrength(4)
                .setAgility(4)
                .setDexterity(4)
                .setIntelligence(4));
        var heroId = createHeroPort.create(new PersistHeroCommand()
                .setName(name)
                .setRace(race), powerStatsId);

        return heroId;
    }

    public void cleanDatabase() {
        heroRepository.findAll().forEach(hero -> heroRepository.delete(hero.getId()));
    }

}
