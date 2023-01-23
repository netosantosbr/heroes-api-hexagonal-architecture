package br.com.gubee.service;

import br.com.gubee.annotation.DomainService;
import br.com.gubee.ports.UpdateHeroPort;
import br.com.gubee.ports.model.HeroRespPA;
import br.com.gubee.usecase.UpdateHeroUseCase;
import br.com.gubee.usecase.model.HeroRespIn;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class UpdateHeroService implements UpdateHeroUseCase {

    private final UpdateHeroPort updateHeroPort;

    @Override
    public UUID update(UUID id, HeroRespIn hero) {
        HeroRespPA heroRespPA = new HeroRespPA(hero.getId(), hero.getName(), hero.getRace(),
            hero.getStrength(), hero.getAgility(), hero.getDexterity(), hero.getIntelligence());
        return updateHeroPort.update(id, heroRespPA);
    }
}
