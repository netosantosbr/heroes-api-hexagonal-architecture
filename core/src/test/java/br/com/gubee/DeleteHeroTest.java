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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class DeleteHeroTest extends PostgresSQLContainerConfig {

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


    @Test
    public void deleteHeroSuccessfullyAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var heroId = persistHeroIntoDatabase("Beririum", Race.HUMAN);
        var formattedURI = BASE_URL + "/" + heroId;

        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void deleteWhenIdDoesNotExistsAndCheckItsStatusCodeAndResponseBody() {
        //GIVEN
        var heroId = UUID.randomUUID();
        var formattedURI = BASE_URL + "/" + heroId;

        //WHEN
        var response = restTemplate.exchange(formattedURI, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

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

    @AfterEach
    public void executeAfterEachTestMethod(){
        cleanDatabase();
    }

    public void cleanDatabase() {
        heroRepository.findAll().forEach(hero -> heroRepository.delete(hero.getId()));
    }
}
