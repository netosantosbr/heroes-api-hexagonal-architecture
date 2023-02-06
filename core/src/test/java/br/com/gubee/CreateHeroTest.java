package br.com.gubee;

import br.com.gubee.configuration.PostgresSQLContainerConfig;
import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.CreateHeroPort;
import br.com.gubee.ports.CreatePowerStatsPort;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import br.com.gubee.repository.HeroRepository;
import br.com.gubee.usecase.request.CreateHeroRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class CreateHeroTest extends PostgresSQLContainerConfig {
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
    public void createHeroSuccessfullyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var requestBody = generateHeroRequest();
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");

        var requestFull = new HttpEntity<>(requestBody, requestHeaders);

        //WHEN
        var response = restTemplate.postForEntity(BASE_URL, requestFull, Void.class);

        //THEN
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());

    }

    @Test
    public void createHeroWhenHeroItsInvalidAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var requestBody = generateInvalidHeroRequest();
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");

        var requestFull = new HttpEntity<>(requestBody, requestHeaders);

        //WHEN
        var response = restTemplate.postForEntity(BASE_URL, requestFull, Void.class);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

    }

    public CreateHeroRequest generateHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Opawa")
                .race(Race.HUMAN)
                .strength(3)
                .agility(4)
                .dexterity(2)
                .intelligence(6)
                .build();
    }

    public CreateHeroRequest generateInvalidHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Opawa")
                .race(Race.HUMAN)
                .strength(12)
                .agility(4)
                .dexterity(2)
                .intelligence(-5)
                .build();
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
