package br.com.gubee.usecase;


import br.com.gubee.usecase.model.HeroRespIn;

import java.util.UUID;

public interface UpdateHeroUseCase {
    UUID update(UUID id, HeroRespIn hero);
}
