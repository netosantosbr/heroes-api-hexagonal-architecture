package br.com.gubee.usecase;


import br.com.gubee.usecase.model.HeroRespIn;

import java.util.List;

public interface FindAllHeroUseCase {
    List<HeroRespIn> findAll();
}
