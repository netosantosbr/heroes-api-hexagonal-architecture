package br.com.gubee.service.stubs;

import br.com.gubee.model.Hero;
import br.com.gubee.ports.*;
import br.com.gubee.ports.model.HeroCompareRespPA;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.ports.request.PersistHeroCommand;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class HeroRepositoryInMemory implements CompareTwoHeroesPort, CreateHeroPort,
        DeleteHeroPort, FindAllHeroPort, FindHeroByIdPort, FindHeroByNamePort, UpdateHeroPort, FindPowerStatsIdFromHeroPort {

    private List<Hero> heroesDatabaseInMemory = new ArrayList<>();

    private static PowerStatsRepositoryInMemory psInMemory;

    public HeroRepositoryInMemory(PowerStatsRepositoryInMemory psInMemory) {
        this.psInMemory = psInMemory;
    }


    @Override
    public HeroCompareRespPA compareTwoHeroes(UUID firstId, UUID secondId) {
        var firstHero = findById(firstId);
        var secondHero = findById(secondId);

        if(firstHero != null && secondHero != null) {
            HeroCompareRespPA response = HeroCompareRespPA.builder()
                    .firstHeroId(firstHero.getId())
                    .secondHeroId(secondHero.getId())
                    .strengthDifference(firstHero.getStrength() - secondHero.getStrength())
                    .agilityDifference(firstHero.getAgility() - secondHero.getAgility())
                    .dexterityDifference(firstHero.getDexterity() - secondHero.getDexterity())
                    .intelligenceDifference(firstHero.getIntelligence() - secondHero.getIntelligence())
                    .build();

            return response;
        }
        throw new NoSuchElementException();

    }

    @Override
    public UUID create(PersistHeroCommand hero, UUID powerStatsUUID) {
        var heroPersisted = new Hero(hero.getName(), hero.getRace(), powerStatsUUID);
        if(psInMemory.findById(powerStatsUUID) != null) {
            heroPersisted.setId(UUID.randomUUID());
            heroPersisted.setCreatedAt(Instant.now());
            heroPersisted.setUpdatedAt(Instant.now());
            heroesDatabaseInMemory.add(heroPersisted);
            return heroPersisted.getId();
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        boolean found = false;
        for(int i = 0; i < heroesDatabaseInMemory.size(); i++) {
            if(heroesDatabaseInMemory.get(i).getId() == id) {
                heroesDatabaseInMemory.remove(heroesDatabaseInMemory.get(i));
                found = true;
            }
        }
        if(!found) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public List<HeroRespPA> findAll() {
        List<HeroRespPA> listOfHeroRespPA = new ArrayList<>();
        heroesDatabaseInMemory.forEach(hero -> {
            var powerStats = psInMemory.findById(hero.getPowerStatsId());
            listOfHeroRespPA.add(new HeroRespPA()
                    .setId(hero.getId())
                    .setName(hero.getName())
                    .setRace(hero.getRace())
                    .setStrength(powerStats.getStrength())
                    .setAgility(powerStats.getAgility())
                    .setDexterity(powerStats.getDexterity())
                    .setIntelligence(powerStats.getIntelligence()));
        });
        return listOfHeroRespPA;
    }

    @Override
    public HeroRespPA findById(UUID id) {
        for (var hero : heroesDatabaseInMemory) {
            if(hero.getId() == id) {
                var powerStats = psInMemory.findById(hero.getPowerStatsId());
                return new HeroRespPA()
                        .setId(hero.getId())
                        .setName(hero.getName())
                        .setRace(hero.getRace())
                        .setStrength(powerStats.getStrength())
                        .setAgility(powerStats.getAgility())
                        .setDexterity(powerStats.getDexterity())
                        .setIntelligence(powerStats.getIntelligence());
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<HeroRespPA> findByName(String name) {
        var found = false;
        List<HeroRespPA> response = new ArrayList<>();
        for (var hero : heroesDatabaseInMemory) {
            if(hero.getName().contains(name)) {
                var powerStats = psInMemory.findById(hero.getPowerStatsId());
                response.add(new HeroRespPA()
                        .setId(hero.getId())
                        .setName(hero.getName())
                        .setRace(hero.getRace())
                        .setStrength(powerStats.getStrength())
                        .setAgility(powerStats.getAgility())
                        .setDexterity(powerStats.getDexterity())
                        .setIntelligence(powerStats.getIntelligence()));
                found = true;
            }
        }
        if(found) {
            return response;
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public UUID findPowerStatsIdFromHero(UUID id) {
        for (var hero : heroesDatabaseInMemory) {
            if(hero.getId() == id) {
                return hero.getPowerStatsId();
            }
        }
        return null;
    }

    @Override
    public UUID update(UUID id, HeroRespPA hero) {
        for (var h : heroesDatabaseInMemory) {
            if(h.getId() == id) {
                var powerStats = psInMemory.findById(h.getPowerStatsId());

                h.setName(hero.getName());
                h.setRace(hero.getRace());
                h.setUpdatedAt(Instant.now());

                powerStats.setStrength(hero.getStrength());
                powerStats.setAgility(hero.getAgility());
                powerStats.setDexterity(hero.getDexterity());
                powerStats.setIntelligence(hero.getIntelligence());

                return h.getId();
            }
        }
        throw new NoSuchElementException();
    }
}
