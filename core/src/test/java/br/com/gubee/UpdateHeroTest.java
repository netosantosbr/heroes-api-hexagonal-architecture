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
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class UpdateHeroTest extends PostgresSQLContainerConfig {
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
    public void updateHeroSuccessfullyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var heroId = persistHeroIntoDatabase("Oririon", Race.HUMAN, 2, 3, 4, 1);
        var requestEntity = createHeroRespIn();
        var formattedURI = BASE_URL + "/" + heroId;
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");

        var requestFull = new HttpEntity<>(requestEntity, requestHeaders);


        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.PUT, requestFull, HeroRespIn.class);

        //THEN
        var hero = heroRepository.findById(heroId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        assertEquals("Emeritus", hero.getName());
        assertEquals(Race.ALIEN, hero.getRace());
        assertEquals(10, hero.getStrength());
        assertEquals(5, hero.getAgility());
        assertEquals(8, hero.getDexterity());
        assertEquals(10, hero.getIntelligence());
    }

    @Test
    public void updateHeroWhenHeroDoesNotExistsAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var formattedURI = BASE_URL + "/" + UUID.randomUUID();
        var requestEntity = createHeroRespIn();
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");
        var requestFull = new HttpEntity<>(requestEntity, requestHeaders);

        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.PUT, requestFull, HeroRespIn.class);

        //THEN
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

    public HeroRespIn createHeroRespIn() {
        return new HeroRespIn()
                .setName("Emeritus")
                .setRace(Race.ALIEN)
                .setStrength(10)
                .setAgility(5)
                .setDexterity(8)
                .setIntelligence(10);
    }

    public void cleanDatabase() {
        heroRepository.findAll().forEach(hero -> heroRepository.delete(hero.getId()));
    }

}
