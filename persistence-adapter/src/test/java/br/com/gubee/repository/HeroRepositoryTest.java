package br.com.gubee.repository;

import br.com.gubee.configuration.PostgresSQLContainerConfig;
import br.com.gubee.model.enums.Race;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.ports.request.PersistHeroCommand;
import br.com.gubee.ports.request.PersistPowerStatsCommand;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.UUID;


@SpringBootTest(properties = "spring.profiles.active=test")
public class HeroRepositoryTest extends PostgresSQLContainerConfig {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @AfterEach
    public void afterEachTest() {
        cleanDatabase();
    }

    @Test
    public void createHeroAndCheckItsPersistence() {
        //GIVEN
        var hero = newHero("Moririum", Race.DIVINE);
        var powerStatsId = newPowerStats();

        //WHEN
        var result = heroRepository.create(hero, powerStatsId);

        //THEN
        assertNotNull(result);
        assertNotNull(heroRepository.findById(result));
        assertEquals("Moririum", heroRepository.findById(result).getName());
    }

    @Test
    public void findAllHeroesAndReturnTwoHeroes() {
        //GIVEN
        heroRepository.create(newHero("Meg", Race.CYBORG), newPowerStats());
        heroRepository.create(newHero("Mog", Race.HUMAN), newPowerStats());

        //WHEN
        var list = heroRepository.findAll();

        //THEN
        assertEquals(2, list.size());
    }

    @Test
    public void findAllHeroesAndReturnEmptyList() {
        //WHEN
        var list = heroRepository.findAll();

        //THEN
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void findByNameAndReturnTwoHeroes() {
        //GIVEN
        heroRepository.create(newHero("Meg", Race.CYBORG), newPowerStats());
        heroRepository.create(newHero("Mog", Race.HUMAN), newPowerStats());
        var searchValue = "m";

        //WHEN
        var listOfHeroesFound = heroRepository.findByName(searchValue);

        //THEN
        assertEquals(2, listOfHeroesFound.size());
        assertEquals("Meg", listOfHeroesFound.get(0).getName());
        assertEquals("Mog", listOfHeroesFound.get(1).getName());
    }

    @Test
    public void findByNameAndReturnNone() {
        //GIVEN
        var searchValue = "m";

        //WHEN
        var listOfHeroesFound = heroRepository.findByName(searchValue);

        //THEN
        assertNotNull(listOfHeroesFound);
        assertEquals(0, listOfHeroesFound.size());
    }

    @Test
    public void findByIdAndReturnACorrectHero() {
        //GIVEN
        var id = heroRepository.create(newHero("Meg", Race.CYBORG), newPowerStats());

        //WHEN
        var hero = heroRepository.findById(id);

        //THEN
        assertNotNull(hero);
        assertEquals("Meg", hero.getName());
        assertEquals(Race.CYBORG, hero.getRace());
    }

    @Test
    public void findByIdAndReturnNone() {
        //GIVEN
        var id = UUID.randomUUID();

        //WHEN - THEN
        assertThrows(NoSuchElementException.class, () -> heroRepository.findById(id));
    }

    @Test
    public void compareTwoHeroesAndCheckItsStatus() {
        //GIVEN
        var firstHeroId = heroRepository.create(newHero("Bog", Race.CYBORG), newPowerStats());
        var secondHeroId = heroRepository.create(newHero("Beg", Race.HUMAN), newPowerStats());

        //WHEN
        var comparedHero = heroRepository.compareTwoHeroes(firstHeroId, secondHeroId);

        //THEN
        assertNotNull(comparedHero);
        assertEquals(0, comparedHero.getStrengthDifference());
        assertEquals(0, comparedHero.getAgilityDifference());
        assertEquals(0, comparedHero.getDexterityDifference());
        assertEquals(0, comparedHero.getIntelligenceDifference());
    }

    @Test
    public void updateAHeroAndCheckItsInfo() {
        //GIVEN
        var heroId = heroRepository.create(newHero("Persian", Race.HUMAN), newPowerStats());
        var heroRespPA = new HeroRespPA().setName("Borizian")
                .setRace(Race.CYBORG)
                .setStrength(6)
                .setAgility(1)
                .setDexterity(2)
                .setIntelligence(2);

        //WHEN
        heroRepository.update(heroId, heroRespPA);

        //THEN
        assertEquals("Borizian", heroRepository.findById(heroId).getName());
        assertEquals(Race.CYBORG, heroRepository.findById(heroId).getRace());
        assertEquals(6, heroRepository.findById(heroId).getStrength());
        assertEquals(1, heroRepository.findById(heroId).getAgility());
        assertEquals(2, heroRepository.findById(heroId).getDexterity());
        assertEquals(2, heroRepository.findById(heroId).getIntelligence());
    }

    @Test
    public void deleteAHeroAndCheckIfItWasRemoved() {
        //GIVEN
        var id = heroRepository.create(newHero("Mecatron", Race.ALIEN), newPowerStats());

        //WHEN
        heroRepository.delete(id);

        //THEN
        assertThrows(NoSuchElementException.class, () -> heroRepository.findById(id));
    }



    public void cleanDatabase() {
        heroRepository.findAll().forEach(hero -> heroRepository.delete(hero.getId()));
    }

    public PersistHeroCommand newHero(String name, Race race) {
        return new PersistHeroCommand()
                .setName(name)
                .setRace(race);
    }

    public UUID newPowerStats() {
        return powerStatsRepository.create(new PersistPowerStatsCommand()
                .setStrength(5)
                .setAgility(5)
                .setDexterity(5)
                .setIntelligence(5));
    }
}
