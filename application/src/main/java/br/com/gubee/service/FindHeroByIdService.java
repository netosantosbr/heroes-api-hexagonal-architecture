package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.FindHeroByIdPort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.service.exceptions.HeroNotFoundException;
import br.com.gubee.usecase.FindHeroByIdUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class FindHeroByIdService implements FindHeroByIdUseCase {

    private final FindHeroByIdPort findHeroByIdPort;

    @Override
    public HeroRespIn findById(UUID id){
            HeroRespPA heroRespWA;
            try {
                heroRespWA = findHeroByIdPort.findById(id);
            } catch (NoSuchElementException ex) {
                throw new HeroNotFoundException();
            }
            return new HeroRespIn()
                    .setId(heroRespWA.getId())
                    .setName(heroRespWA.getName())
                    .setRace(heroRespWA.getRace())
                    .setStrength(heroRespWA.getStrength())
                    .setAgility(heroRespWA.getAgility())
                    .setDexterity(heroRespWA.getDexterity())
                    .setIntelligence(heroRespWA.getIntelligence());

    }
}
