package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.UpdateHeroPort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.exceptions.HeroNotFoundException;
import br.com.gubee.usecase.UpdateHeroUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class UpdateHeroService implements UpdateHeroUseCase {

    private final UpdateHeroPort updateHeroPort;

    @Override
    public UUID update(UUID id, HeroRespIn hero) {
        var heroRespPA = new HeroRespPA()
                .setId(hero.getId())
                .setName(hero.getName())
                .setRace(hero.getRace())
                .setStrength(hero.getStrength())
                .setAgility(hero.getAgility())
                .setDexterity(hero.getDexterity())
                .setIntelligence(hero.getIntelligence());

        try {
            return updateHeroPort.update(id, heroRespPA);
        } catch(NoSuchElementException ex) {
            throw new HeroNotFoundException();
        }
    }
}
