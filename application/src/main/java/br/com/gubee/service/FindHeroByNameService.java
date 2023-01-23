package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.FindHeroByNamePort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.usecase.FindHeroByNameUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@DomainService
public class FindHeroByNameService implements FindHeroByNameUseCase {

    private final FindHeroByNamePort findHeroByNamePort;

    @Override
    public List<HeroRespIn> findByName(String name) {
        List<HeroRespIn> convertedHeroesList = new ArrayList<>();
        List<HeroRespPA> heroesList = findHeroByNamePort.findByName(name);

        for(HeroRespPA hero : heroesList) {
            convertedHeroesList.add(
                    new HeroRespIn(hero.getId(), hero.getName(), hero.getRace(),
                            hero.getStrength(), hero.getAgility(), hero.getDexterity(), hero.getIntelligence()));
        }

        return convertedHeroesList;
    }
}
