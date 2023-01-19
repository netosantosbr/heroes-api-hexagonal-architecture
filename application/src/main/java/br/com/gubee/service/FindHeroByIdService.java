package br.com.gubee.service;

import br.com.gubee.ports.FindHeroByIdPort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.usecase.FindHeroByIdUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FindHeroByIdService implements FindHeroByIdUseCase {

    private final FindHeroByIdPort findHeroByIdPort;

    @Override
    public HeroRespIn findById(UUID id) {
        HeroRespPA heroRespWA = findHeroByIdPort.findById(id);

        return new HeroRespIn(heroRespWA.getId(), heroRespWA.getName(), heroRespWA.getRace(),
                heroRespWA.getStrength(), heroRespWA.getAgility(), heroRespWA.getDexterity(), heroRespWA.getIntelligence());
    }
}
