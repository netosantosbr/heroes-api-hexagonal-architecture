package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.FindHeroByNamePort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.service.exceptions.HeroNotFoundException;
import br.com.gubee.usecase.FindHeroByNameUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@DomainService
public class FindHeroByNameService implements FindHeroByNameUseCase {

    private final FindHeroByNamePort findHeroByNamePort;

    @Override
    public List<HeroRespIn> findByName(String name) {
        List<HeroRespIn> convertedHeroesList = new ArrayList<>();
        List<HeroRespPA> heroesList;
        try{
             heroesList = findHeroByNamePort.findByName(name);
        } catch (NoSuchElementException ex) {
            throw new HeroNotFoundException();
        }

        for(HeroRespPA hero : heroesList) {
            convertedHeroesList.add(
                    new HeroRespIn()
                            .setId(hero.getId())
                            .setName(hero.getName())
                            .setRace(hero.getRace())
                            .setStrength(hero.getStrength())
                            .setAgility(hero.getAgility())
                            .setDexterity(hero.getDexterity())
                            .setIntelligence(hero.getIntelligence()));
        }

        return convertedHeroesList;
    }
}
