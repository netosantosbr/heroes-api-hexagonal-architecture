package br.com.gubee.usecase;



import br.com.gubee.usecase.model.HeroRespIn;

import java.util.UUID;

public interface FindHeroByIdUseCase {
    HeroRespIn findById(UUID id);
}
