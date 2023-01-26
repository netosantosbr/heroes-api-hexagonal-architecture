package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.FindAllHeroPort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.usecase.FindAllHeroUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@DomainService
public class FindAllHeroService implements FindAllHeroUseCase {

    private final FindAllHeroPort findAllHeroPort;

    @Override
    public List<HeroRespIn> findAll() {
        List<HeroRespIn> convertedHeroesList = new ArrayList<>();
        List<HeroRespPA> heroesList = findAllHeroPort.findAll();

        for(var hero : heroesList) {
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
