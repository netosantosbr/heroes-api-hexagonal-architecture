package br.com.gubee.usecase;


import br.com.gubee.usecase.model.HeroCompareRespIn;

import java.util.UUID;

public interface CompareTwoHeroesUseCase {
    HeroCompareRespIn compareTwoHeroes(UUID firstId, UUID secondId);
}
