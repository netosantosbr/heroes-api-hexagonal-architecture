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

        for(HeroRespPA hero : heroesList) {
            convertedHeroesList.add(
                    new HeroRespIn(hero.getId(), hero.getName(), hero.getRace(),
                            hero.getStrength(), hero.getAgility(), hero.getDexterity(), hero.getIntelligence()));
        }

        return convertedHeroesList;
    }
}
